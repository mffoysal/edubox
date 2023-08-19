package com.edubox.admin.sms;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.edubox.admin.R;

public class NotificationHelper {
    private Context context;
    private String channelId;

    public NotificationHelper(Context context, String channelId) {
        this.context = context;
        this.channelId = channelId;
    }

    public void showDefaultNotification(String title, String content, String data) {
        NotificationCompat.Builder builder = buildNotification(title, content, data);
        showNotification(builder);
    }

    public void showBigTextNotification(String title, String content, String bigText, String data) {
        NotificationCompat.Builder builder = buildNotification(title, content, data);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
        showNotification(builder);
    }

    public void showInboxNotification(String title, String content, String[] messages, String data) {
        NotificationCompat.Builder builder = buildNotification(title, content, data);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setSummaryText("Inbox");
        for (String message : messages) {
            inboxStyle.addLine(message);
        }
        builder.setStyle(inboxStyle);
        showNotification(builder);
    }

    public void showImageNotification(String title, String content, int imageResId, String data) {
        NotificationCompat.Builder builder = buildNotification(title, content, data);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));
        showNotification(builder);
    }

    public void showProgressNotification(String title, String content, int progress, String data) {
        NotificationCompat.Builder builder = buildNotification(title, content, data);
        builder.setProgress(100, progress, false);
        showNotification(builder);
    }

    public void showCustomNotification(String title, String content, int layoutResId, String data) {
        NotificationCompat.Builder builder = buildNotification(title, content, data);
        builder.setCustomContentView(getCustomContentView(layoutResId));
        showNotification(builder);
    }

    private NotificationCompat.Builder buildNotification(String title, String content, String data) {
        Intent intent = new Intent(context, NotifyActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("content",content);
        intent.putExtra("title",title);
        intent.setData(Uri.parse(data));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        return builder;
    }


    private void showNotification(NotificationCompat.Builder builder) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private RemoteViews getCustomContentView(int layoutResId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutResId);
        // Customize the views in your custom notification layout here
        return remoteViews;
    }
}
