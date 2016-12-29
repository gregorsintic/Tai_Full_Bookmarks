package com.example.rok.terroristinfo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Created by Rok on 9.11.2016.
 */
public class BackgroundAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)  {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
        //PARTIAL_WAKE_LOCK
        wl.acquire();

        //new RequestNotifyTask(context).execute("http://10.10.10.100:8888/handler");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Build the notification using Notification.Builder
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentTitle("wut")
                .setContentText("wut");

        //Show the notification
        notificationManager.notify(1, builder.build());

        wl.release();
    }

    public void setAlarm(Context context) {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, BackgroundAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pi); // Millisec * Second * Minute
    }
}
