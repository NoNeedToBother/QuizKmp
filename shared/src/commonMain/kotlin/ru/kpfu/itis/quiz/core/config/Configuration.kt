package ru.kpfu.itis.quiz.core.config

data class Configuration(
    val platformConfiguration: PlatformConfiguration,
    val isHttpLoggingEnabled: Boolean,
    val isDebug: Boolean,
) {

    enum class DeviceType {
        Tablet,
        Phone,
    }
}
