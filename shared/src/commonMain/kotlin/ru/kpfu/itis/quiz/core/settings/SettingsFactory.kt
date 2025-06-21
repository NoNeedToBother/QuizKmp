package ru.kpfu.itis.quiz.core.settings

import com.russhwolf.settings.Settings
import ru.kpfu.itis.quiz.core.config.PlatformConfiguration

expect class SettingsFactory() {

    fun create(name: String, conf: PlatformConfiguration): Settings

}