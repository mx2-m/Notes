package com.example.pora_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Note;
import com.example.lib.Notes;
import com.example.pora_app.events.MyEventDataChange;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

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

            Intent intent = getIntent();
            final int position = intent.getIntExtra("position", 2);
            Log.d(TAG, "POSITION " + position);

            app.getList().edit(position, tmp);
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


    public void Exit(View view) {
        EditActivity.this.finish();
        System.exit(0);
    }

    private void clearForm() {
        etContent.getText().clear();
        etTitle.getText().clear();
        editTextDate.getText().clear();
    }
}
