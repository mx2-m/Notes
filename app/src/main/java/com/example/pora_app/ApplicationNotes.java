package com.example.pora_app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.lib.Note;
import com.example.lib.Notes;
import com.example.pora_app.events.MyEventDataChange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.example.pora_app.events.MyEventDataChange.MyEnumType.delete;
import static com.example.pora_app.events.MyEventDataChange.MyEnumType.newdata;

/**
 * Why do we need UUID?
 * Track signed-out user preferences (saving per device state without a user account)
 * Generate signed-out or anonymous user analytics
 * Handle multiple installations across different devices
 * Enforce free content limits
 *
 * Probability that UUID will be duplicated is very low
 */

public class ApplicationNotes extends Application {
    public static final String TAG = ApplicationNotes.class.getSimpleName();
    private static final String MY_FILE_NAME = "podatki.json";
    private Notes list;
    Gson gson;
    File file;
    int added;
    int deleted;

    public static final String APP_ID = "APP_ID_KEY";
    SharedPreferences sharedPreferences;
    String idAPP;

    public void setAppId() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.contains(APP_ID)) //READ IT FROM FILE
            idAPP = sharedPreferences.getString(APP_ID, "DEFAULT VALUE ERR");
        else { //FIRST TIME GENERATE ID AND SAVE IT
            idAPP = UUID.randomUUID().toString().replace("-", "");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APP_ID, idAPP);
            editor.apply();
        }
        // list.setUserID(idAPP);
        Log.d(TAG, "id:" + idAPP);

    }

    private void initData() {
        setAppId();
        list = new Notes(idAPP);
        readFromFile();
        added = 0;
        deleted = 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        initData();
    }

    public Notes getList() {
        return list;
    }

    public void saveData() {
        saveToFile();
        Log.d(TAG, "SPREMLJENO U FAJL");
    }


    private Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }

    private File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir, MY_FILE_NAME);
        }
        Log.i(TAG, file.getPath());
        return file;
    }

    private void saveToFile() {
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(list));
        } catch (IOException e) {
            Log.d(TAG, "Can't save " + file.getPath());
        }
    }

    private boolean readFromFile() {
        if (!getFile().exists()) return false;
        try {
            list = getGson().fromJson(FileUtils.readFileToString(getFile()), Notes.class);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEventDataChange event) {
        Log.i(TAG, "onMessageEvent" + event.toString());
        switch (event.getMyDataType()) {
            case delete:
                deleted++;
                break;
            case newdata:
                added++;
                break;
        }
        Toast.makeText(this, "Activity: deleted:" + deleted + " added:" + added, Toast.LENGTH_LONG).show();
    }


}
