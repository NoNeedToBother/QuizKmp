package ru.kpfu.itis.quiz.profiles.presentation.mvi

import ru.kpfu.itis.quiz.core.core.model.User

data class OtherUserProfileScreenState(
    val user: User? = null,
)
