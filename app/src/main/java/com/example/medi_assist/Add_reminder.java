package com.example.medi_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Add_reminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String FOLDERNAME = "AlarmsHistory";
    public static final String TAG = "nie udalo sie";

    private NotificationManagerCompat notificationManager;
    public static List<Reminder> reminders = new ArrayList<>();
    private EditText etName;
    private EditText etInterval;
    private TextView tvTime;
    private TextView tvDone;
    private Button btnPickTime;
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        notificationManager = NotificationManagerCompat.from(this);

        etName = findViewById(R.id.etName);
        etInterval = findViewById(R.id.etInterval);
        btnPickTime = findViewById(R.id.btnPickTime);
        tvTime = findViewById(R.id.tvTime);
        tvDone = findViewById(R.id.fading_text_view);
        tvDone.setVisibility(View.INVISIBLE);
    }

    public void setTimer(View v) {
        LocalDate today = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            String FILENAME = ("alarms.txt");

            Context context = getApplicationContext();
            String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
            File subFolder = new File(folder);

            if (!subFolder.exists()) subFolder.mkdirs();

            try (FileOutputStream os = new FileOutputStream(new File(subFolder, FILENAME), true)) {
                os.write((etName.getText() + "\t" + tvTime.getText() + "\n").getBytes());

            } catch (FileNotFoundException e) {
                Log.e(TAG, e.toString());
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }

            setReminders();

            tvDone.setVisibility(View.VISIBLE);
        }

        startAlarm(calendar);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)   //in res/drawable -> new VectorAssets (ic_baseline_local_hospital_24.xml)
//                .setContentTitle("Time to take " + name +"!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_REMINDER)
//                .build();
//
//        notificationManager.notify(1, notification);

    }

    public void timePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "timePicker");
        //btnPickTime.setText("change time");

        tvDone.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        calendar = c;

        updateTimeText(c);

    }

    private void updateTimeText(Calendar c){
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        tvTime.setText(timeText);
    }

    private void startAlarm(Calendar c){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(this, 1, intent, 0);


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        double interval = Double.parseDouble(
                String.valueOf(etInterval.getText()));
        interval = interval*60*60*1000;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), ((int) interval), pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,intent, 0);

        alarmManager.cancel(pendingIntent);
        //set text alarm cancelled
    }

    private void setReminders(){

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
                    reminders.add(new Reminder(s[0], s[1]));
                }


            } catch (FileNotFoundException e) {
                Log.e("blad", e.toString());
            } catch (IOException e) {
                Log.e("blad", e.toString());
            }
        }
    }
}