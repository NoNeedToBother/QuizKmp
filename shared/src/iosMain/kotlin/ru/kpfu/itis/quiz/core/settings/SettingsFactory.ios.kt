package ru.kpfu.itis.quiz.core.settings

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import ru.kpfu.itis.quiz.core.config.PlatformConfiguration

actual class SettingsFactory actual constructor() {

    actual fun create(
        name: String,
        conf: PlatformConfiguration
    ): Settings = NSUserDefaultsSettings.Factory().create(name = name)

}
