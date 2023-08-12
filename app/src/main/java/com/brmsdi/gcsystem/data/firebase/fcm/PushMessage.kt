package com.brmsdi.gcsystem.data.firebase.fcm

import android.content.Context
import androidx.core.app.NotificationCompat
import com.brmsdi.gcsystem.R
/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface PushMessage {

    fun builder(
        context: Context,
        title: String,
        body: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        channel: String
    ): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, channel)
        builder.setSmallIcon(R.drawable.baseline_apartment_24_2)
        builder.setContentTitle(title)
        builder.setContentText(body)
        builder.priority = priority
        return builder
    }

    fun showNotification(context: Context, title: String, body: String)
}