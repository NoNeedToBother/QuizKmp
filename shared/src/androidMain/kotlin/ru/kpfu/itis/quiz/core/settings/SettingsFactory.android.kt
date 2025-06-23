package ru.kpfu.itis.quiz.core.settings


import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import ru.kpfu.itis.quiz.core.config.PlatformConfiguration

actual class SettingsFactory actual constructor() {

    actual fun create(
        name: String,
        conf: PlatformConfiguration
    ): Settings = SharedPreferencesSettings(
        delegate = conf.androidContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    )

}
