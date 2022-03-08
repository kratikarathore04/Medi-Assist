package com.example.medi_assist;

import static com.example.medi_assist.App.CHANNEL_1_ID;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int n = 1;

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)   //in res/drawable -> new VectorAssets (ic_baseline_local_hospital_24.xml)
                .setContentTitle("Time to take " + "your meds" + "!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(1, notification);
    }

}