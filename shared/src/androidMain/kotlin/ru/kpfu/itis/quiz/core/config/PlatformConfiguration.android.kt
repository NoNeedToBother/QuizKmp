package ru.kpfu.itis.quiz.core.config

import android.content.Context
import ru.kpfu.itis.quiz.core.firebase.FirebaseScreenAnalytics

actual class PlatformConfiguration(
    val androidContext: Context,
    actual val appVersionName: String,
    actual val appVersionNumber: String,
    actual val osVersion: String,
    actual val deviceType: Configuration.DeviceType,
    actual val analytics: FirebaseScreenAnalytics,
)
