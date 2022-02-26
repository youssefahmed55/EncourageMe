package com.encourage.encourageme.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.encourage.encourageme.R;

import java.util.ArrayList;

public class ReminderBroadcast extends BroadcastReceiver {

    private static final String TAG = "meyoussef";


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifylemubit")
                                            .setSmallIcon(R.mipmap.encourage)
                                            .setContentText("Encourage me")
                                            .setContentText(intent.getExtras().getString("myid"))
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0,builder.build());


        Log.d(TAG, "iam here on Recive");
    }

}
