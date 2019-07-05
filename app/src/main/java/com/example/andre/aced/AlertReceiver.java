package com.example.andre.aced;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import model.Checklist;



public class AlertReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder
                (context, Notification_Channels.checkListPriority);

        Intent intent1  = new Intent(context, com.example.andre.aced.Checklist.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //Builds Notification
        builder.setSmallIcon(R.drawable.noti);
        builder.setContentTitle("You Have A Incomplete Task!");
        builder.setContentText(com.example.andre.aced.Checklist.titleTask);
        builder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}
