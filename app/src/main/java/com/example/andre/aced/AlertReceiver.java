package com.example.andre.aced;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlertReceiver extends BroadcastReceiver {

    @SuppressLint("ResourceAsColor")
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder
                (context, Notification_Channels.checkListUserInput);

        Intent broadCastIntent = new Intent(context, Checklist.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent broadCastIntent1 = new Intent(context, Checklist.class);
        broadCastIntent1.setAction("menuFragment");
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 1 , broadCastIntent1, PendingIntent.FLAG_UPDATE_CURRENT);


        //Builds Notification
        builder.setSmallIcon(R.drawable.noti);
        builder.setContentTitle("You Have A Incomplete Task!");
        builder.setContentText(com.example.andre.aced.Checklist.titleTask);
        builder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent );
        builder.setColor(R.color.colorPrimaryDark);

        notificationManager.notify(1, builder.build());

        /*NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.support.v4.app.NotificationCompat.Builder builder1 = new android.support.v4.app.NotificationCompat.Builder
                (context, Notification_Channels.remindClassSchedule);



        Intent broadCastIntent2 = new Intent(context, WeeklyView.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, WeeklyView.requestCode, broadCastIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent broadCastIntent3 = new Intent(context, WeeklyView.class);
        broadCastIntent1.setAction("menuFragment");
        PendingIntent pendingIntent3 = PendingIntent.getActivity(context, WeeklyView.requestCode , broadCastIntent3, PendingIntent.FLAG_UPDATE_CURRENT);


        //Builds Notification
        builder1.setSmallIcon(R.drawable.noti);
        builder1.setContentTitle("Upcoming Class!");
        builder1.setContentText(intent.getStringExtra("coursename"));
        builder1.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_HIGH);
        builder1.setAutoCancel(true);
        builder1.setContentIntent(pendingIntent2);
        builder1.setColor(R.color.colorPrimaryDark);

        notificationManager1.notify(WeeklyView.requestCode, builder1.build());*/
    }
}
