package ru.kpfu.itis.quiz.core.firebase

interface FirebaseScreenAnalytics {
    fun log(event: ScreenEvent)
}

enum class ScreenEvent {
    LAUNCH_SIGN_IN, LAUNCH_REGISTER,
    LAUNCH_HOME,
    LAUNCH_QUESTIONS, LAUNCH_QUESTION_SETTINGS,
    LAUNCH_PROFILE, LAUNCH_OTHER_USER_PROFILE,
    LAUNCH_SEARCH,
    LAUNCH_LEADERBOARD,
}
