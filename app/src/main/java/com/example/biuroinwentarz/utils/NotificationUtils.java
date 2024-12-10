package com.example.biuroinwentarz.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.biuroinwentarz.R;

public class NotificationUtils {
    private static final String CHANNEL_ID = "inwentarz_channel";

    public static void sendNotification(Context context, String message) {
        try {
            createNotificationChannel(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_inventory)
                    .setContentTitle("Powiadomienie")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        } catch (SecurityException e) {
            Log.e("NotificationUtils", "Permission not granted to send notifications.", e);
        }
    }

    private static void createNotificationChannel(Context context) {
        CharSequence name = "InwentarzChannel";
        String description = "Kanał powiadomień o inwentarzu";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}