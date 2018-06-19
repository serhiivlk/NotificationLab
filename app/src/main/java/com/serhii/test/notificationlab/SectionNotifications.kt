package com.serhii.test.notificationlab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.ArrayMap
import java.util.concurrent.atomic.AtomicInteger

private const val CHANNEL_ID = "some_channel_id"

class SectionNotifications(private val context: Context) {
    private val counter = AtomicInteger()

    // map section to notifications
    private val notificationMap = ArrayMap<Int, MutableSet<Int>>()

    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        createNotificationChannel()
    }

    fun createNotificationFromSection(sectionNumber: Int) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text, sectionNumber))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationId = counter.incrementAndGet()

        with(notificationMap) {
            get(sectionNumber)?.add(notificationId)
                    ?: put(sectionNumber, hashSetOf(notificationId))
        }

        notificationManager.notify(notificationId, builder.build())
    }

    fun cancelNotificationFromSection(sectionNumber: Int) {
        notificationMap.keys.filter { it >= sectionNumber }.flatMap { notificationMap[it]!! }
                .forEach {
                    notificationManager.cancel(it)
                }
    }

    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }

    // for sdk 26+
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}