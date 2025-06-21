package ru.kpfu.itis.quiz.core

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ru.kpfu.itis.quiz.core.config.Configuration
import ru.kpfu.itis.quiz.core.config.PlatformConfiguration
import ru.kpfu.itis.quiz.core.network.networkModule
import ru.kpfu.itis.quiz.platformModule

fun initKoin(
    configuration: Configuration,
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            createConfiguration(configuration),
            networkModule,
            //qualifierModule,

            platformModule(),

            //featureModule,
        )
    }
}

private fun createConfiguration(configuration: Configuration) = module {
    single<Configuration> { configuration }
    single<PlatformConfiguration> { configuration.platformConfiguration }
}