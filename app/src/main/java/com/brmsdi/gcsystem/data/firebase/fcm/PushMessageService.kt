package com.brmsdi.gcsystem.data.firebase.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PushMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        TODO("ADICIONAR TOKEN AO BACKEND")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification = message.notification
        notification?.let {
            val title = it.title ?: ""
            val body = it.body ?: ""
            it.channelId?.let {channelID ->
                val pushMessage = get<PushMessage>(named(channelID))
                pushMessage.showNotification(this, title, body)
            }
        }
    }
}