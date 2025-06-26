package ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other

import kotlinx.serialization.Serializable
import ru.kpfu.itis.quiz.feature.profile.presentation.model.Result
import ru.kpfu.itis.quiz.feature.profile.presentation.model.User

@Serializable
data class OtherUserProfileScreenState(
    val user: User? = null,
    val results: List<Result> = emptyList(),
)
