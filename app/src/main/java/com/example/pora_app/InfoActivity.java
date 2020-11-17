package com.example.pora_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.SystemUpdateInfo;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.PhantomReference;

public class InfoActivity extends AppCompatActivity {


    TextView ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ID = findViewById(R.id.ID);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String id = sharedPreferences.getString("APP_ID_KEY", "");

        String print = "ID is:  " + id;
        ID.setText(print);

    }


    public void Exit(View view) {
        InfoActivity.this.finish();
        System.exit(0);
    }


}