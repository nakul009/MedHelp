package com.example.nakuld.medhelp99;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by nakulD on 4/21/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{
    private final String myBlog = "http://android-er.blogspot.com/";
    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;

    @Override
    public void onReceive(Context context, Intent arg1) {
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
