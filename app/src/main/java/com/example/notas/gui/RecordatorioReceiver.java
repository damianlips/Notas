package com.example.notas.gui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class RecordatorioReceiver extends BroadcastReceiver {
    public static String RECORDATORIO = "com.example.tp3.NOTA";
    public static String TEXTO = "Texto";
    public static String CHANNEL_ID= "XD";
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(intent.getAction().equals(RECORDATORIO)){

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID )
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentTitle("NOTIFIQUEIBOL")
                    .setContentText(intent.getExtras().getString(TEXTO))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigPictureStyle())
//                            .bigPicture(aBigBitmap)
//                            .setBigContentTitle("Large Notification Title"))
                    .setAutoCancel(true);
//                    .setLargeIcon(android.R.drawable.star_on)


            NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context.getApplicationContext());
            notificationManager.notify(0,mBuilder.build());

        }
    }

}
