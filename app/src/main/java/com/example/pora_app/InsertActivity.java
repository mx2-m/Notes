package com.example.pora_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.IllegalNameException;
import com.example.lib.Note;
import com.example.lib.Notes;
import com.example.pora_app.events.MyEventDataChange;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class InsertActivity extends AppCompatActivity {

    public static final String TAG = InsertActivity.class.getName();
    private Notes list;
    public static final String NAME_TAG = "name";
    private EditText etContent;
    private EditText etTitle;
    private EditText editTextDate;
    ApplicationNotes app;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        etContent = findViewById(R.id.etContent);
        etTitle = findViewById(R.id.etTitle);
        editTextDate = findViewById(R.id.editTextDate);

        clearForm();
        initData();
    }

    private void initData() {
        app = (ApplicationNotes) getApplication();
        list = new Notes();
    }

    public void onClickAdd(View view) {
        try {
            Log.d(TAG, "onClickAdd");
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDate.getText().toString());
            Note tmp = new Note(etTitle.getText().toString(), etContent.getText().toString(), date1);

            String dateT = String.format("%1$tb %1$te, %1$tY", date1);
            Log.d(TAG, dateT);
            Log.d(TAG, tmp.toString());
            Log.d(TAG, app.getList().toString());
            app.getList().addNote(tmp);
            EventBus.getDefault().post(new MyEventDataChange(tmp.toString(), MyEventDataChange.MyEnumType.newdata));
            app.saveData();
            clearForm();
            Log.d(TAG, app.getList().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  clearForm();
        // Log.d(TAG, list.toString());

    }


    public void onClickInfo(View view) {
        try {
            Log.d(TAG, "onClickInfo");
            String s = "Duzina notes liste je " + String.valueOf(list.size());
            Log.i(TAG, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Exit(View view) {
        InsertActivity.this.finish();
        System.exit(0);
    }

    private void clearForm() {
        etContent.getText().clear();
        etTitle.getText().clear();
        editTextDate.getText().clear();
    }


}