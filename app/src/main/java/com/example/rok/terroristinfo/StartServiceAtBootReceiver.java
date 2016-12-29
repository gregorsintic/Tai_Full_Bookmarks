package com.example.rok.terroristinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServiceAtBootReceiver extends BroadcastReceiver {
    BackgroundAlarm alarm = new BackgroundAlarm();
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            alarm.setAlarm(context);
        }
    }
}