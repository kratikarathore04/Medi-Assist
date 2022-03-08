package com.example.medi_assist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }
    public void symptoms_check(View v1)
    {
        Intent i1 = new Intent(this,symptoms.class);
        startActivity(i1);
    }
    public void reminder(View v2) {
        Intent i2 = new Intent(this, pill_reminder.class);
        startActivity(i2);
    }
    public void call_amb(View v3){
        Intent i3 = new Intent(this,Emergency_call.class);
        startActivity(i3);
    }
    public void tips(View v4) {
        Intent i4 = new Intent(this, Tips.class);
        startActivity(i4);
    }
}