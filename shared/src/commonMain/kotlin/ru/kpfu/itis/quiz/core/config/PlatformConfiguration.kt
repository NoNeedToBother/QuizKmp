package ru.kpfu.itis.quiz.core.config

import ru.kpfu.itis.quiz.core.firebase.FirebaseScreenAnalytics

expect class PlatformConfiguration {
    val appVersionName: String
    val appVersionNumber: String
    val osVersion: String
    val deviceType: Configuration.DeviceType
    val analytics: FirebaseScreenAnalytics
}
