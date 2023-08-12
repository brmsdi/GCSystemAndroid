package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.data.constants.Constant.NOTIFICATION.CHANNEL1
import com.brmsdi.gcsystem.data.firebase.fcm.OrderServicePushMessage
import com.brmsdi.gcsystem.data.firebase.fcm.PushMessage
import org.koin.core.qualifier.named
import org.koin.dsl.module

val PushModules = module {
    single<PushMessage>(named(CHANNEL1)) { OrderServicePushMessage() }
}