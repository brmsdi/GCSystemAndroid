package com.brmsdi.gcsystem.data.firebase.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.brmsdi.gcsystem.data.constants.Constant.NOTIFICATION.CHANNEL1
import com.brmsdi.gcsystem.data.constants.Constant.NOTIFICATION.CHANNEL1_ID
import java.util.Random

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class OrderServicePushMessage : PushMessage {
    override fun showNotification(
        context: Context,
        title: String,
        body: String
    ) {
        val builder = builder(context, title, body, priority = NotificationCompat.PRIORITY_MAX, CHANNEL1)
        val notificationBigStyle = NotificationCompat.BigTextStyle()
        notificationBigStyle.setBigContentTitle(title)
        notificationBigStyle.setSummaryText(title)
        notificationBigStyle.bigText(body)
        builder.setStyle(notificationBigStyle)
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL1_ID,
                CHANNEL1,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManagerCompat.createNotificationChannel(notificationChannel)
            builder.setChannelId(CHANNEL1_ID)
        }
        notificationManagerCompat.notify(Random().nextInt(), builder.build())
    }
}