package com.example.pora_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.IllegalNameException;
import com.example.lib.Note;
import com.example.lib.Notes;
import com.example.pora_app.events.MyEventDataChange;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ActivityStart extends AppCompatActivity implements AdapterNotes.OnItemClickListener {

    private static final String TAG = ActivityStart.class.getName();
    private static final int MY_ACTIVITY_ID = 201;
    private ApplicationNotes app;
    private AdapterNotes adapter;
    private RecyclerView recyclerView;
    private int selectedPosition;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initData();
        try {
            prepareNotes();
        } catch (IllegalNameException e) {
            e.printStackTrace();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notes");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void prepareNotes() throws IllegalNameException {
        for(int i = 0; i < 100; i++) {
            Date date=new Date(ThreadLocalRandom.current().nextInt() * 1000L);
        Note note = new Note("Note"+i,"Content"+i,date);
            app.getList().addNote(note);
        }
    }


    @Override
    public void onItemClick(View itemView, int position) {
        //Toast.makeText(this,"Selected id:"+position,Toast.LENGTH_LONG).show();
        selectedPosition = position;
        Intent intent = new Intent(this.getBaseContext(), EditActivity.class);
        intent.putExtra("position", position);
        this.startActivity(intent);
        //setResult(RESULT_OK, intent);
        // finish();

    }

    @Override
    public void onItemLongClick(View itemView, int position) {
        selectedPosition = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + app.getList().get(selectedPosition).getName()).setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void removeRow(int position) {
        Note tmp = app.getList().get(position);
        app.getList().remove(position);
        EventBus.getDefault().post(new MyEventDataChange(tmp.toString(), MyEventDataChange.MyEnumType.delete));
        //adapter.notifyDataSetChanged();
        app.saveData();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    removeRow(selectedPosition);
                    //  Toast.makeText(getBaseContext(),"Selected YES:"+data.getList().get(pos), Toast.LENGTH_LONG).show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    //Toast.makeText(getBaseContext(),"Selected NO:"+data.getList().get(pos), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEventDataChange event) {
        Log.i(TAG, "onMessageEvent" + event.toString());
        adapter.notifyDataSetChanged();
        Toast.makeText(this, event.toString(), Toast.LENGTH_SHORT).show();
    }

    ;

    public void initData() {
        app = (ApplicationNotes) getApplication();
        adapter = new AdapterNotes(app);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(this);
    }


    public void onClickOpenInsert(View view) {
        this.startActivityForResult(
                new Intent(this.getBaseContext(), InsertActivity.class),
                MY_ACTIVITY_ID);

    }

    public void onClickOpenInfo(View view) {
        this.startActivityForResult(
                new Intent(this.getBaseContext(), InfoActivity.class),
                MY_ACTIVITY_ID);

    }


    public void Exit(View view) {
        ActivityStart.this.finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this.getBaseContext(), Settings.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
