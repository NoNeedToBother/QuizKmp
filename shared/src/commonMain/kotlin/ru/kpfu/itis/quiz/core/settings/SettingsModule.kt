package ru.kpfu.itis.quiz.core.settings

import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsModule = module {
    singleOf(::SettingsFactory)
    single<Settings> {
        val factory: SettingsFactory = get()
        factory.create("quiz_settings", get())
    }
}