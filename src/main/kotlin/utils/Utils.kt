package utils

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object Utils {

    private const val PREFIX_DISPLAY_ID = "ec-mapper-generator"

    fun createNotification(title: String,
                           message: String,
                           project: Project?,
                           type: NotificationType,
                           listener: NotificationListener?) {
        val stickyNotification = NotificationGroup(
            "$PREFIX_DISPLAY_ID$title",
            NotificationDisplayType.BALLOON,
            true)
        stickyNotification.createNotification(title, message, type, listener).notify(project)
    }

}