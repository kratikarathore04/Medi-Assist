package com.example.medi_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ArrayList<Reminder> reminders;
    public static final String FOLDERNAME = "AlarmsHistory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Lookup the recyclerview in activity layout
        RecyclerView rvReminders  = (RecyclerView) findViewById(R.id.rvReminders);

        //Initialize messages
        reminders = this.createHistory();
        // Create adapter passing in the sample user data
        ItemAdapter adapter = new ItemAdapter(reminders);
        // Attach the adapter to the recyclerview to populate items
        rvReminders.setAdapter(adapter);
        //Set layout manager to position items
        rvReminders.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new ItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if(position == reminders.size()-1){
                    Context context = getApplicationContext();
                    AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, AlertReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
                    alarmManager.cancel(pendingIntent);
                }

                reminders.remove(position);
                updateOnHistory();


            }
        });
    }

    public void updateOnHistory(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            String FILENAME = ("alarms.txt");

            Context context = getApplicationContext();
            String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
            File subFolder = new File(folder);

            if (!subFolder.exists()) subFolder.mkdirs();

            try (FileOutputStream os = new FileOutputStream(new File(subFolder, FILENAME), false)) {

                for (int i = 0; i <reminders.size() ; i++) {
                    os.write((reminders.get(i).getName()+"\t"+ reminders.get(i).getTime()+ "\n").getBytes());
                }

            } catch (FileNotFoundException e) {
                Log.e("BLAD", e.toString());
            } catch (IOException e) {
                Log.e("BLAD", e.toString());
            }
        }
    }

    public ArrayList<Reminder> createHistory(){

        ArrayList<Reminder> messages = new ArrayList<Reminder>();

        Context context = getApplicationContext();
        String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
        File file = new File(folder);
        String[] directories = file.list((current, name) -> name.endsWith(".txt"));


        for (int i = 0; i <directories.length ; i++) {

            try {

                FileInputStream fileInputStream = new FileInputStream(folder+File.separator+directories[i]);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);

                String line;

                while ((line = br.readLine()) != null) {
                    String[] s = line.split("\t");
                    messages.add(new Reminder(s[0], s[1]));
                }


            } catch (FileNotFoundException e) {
                Log.e("blad", e.toString());
            } catch (IOException e) {
                Log.e("blad", e.toString());
            }
        }

        return messages;
    }

    public void onDelete(View view) {

    }
}
