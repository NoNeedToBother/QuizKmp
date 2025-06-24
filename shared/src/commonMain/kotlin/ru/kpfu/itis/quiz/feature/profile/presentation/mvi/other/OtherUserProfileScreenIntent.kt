package ru.kpfu.itis.quiz.feature.profile.presentation.mvi.other

sealed interface OtherUserProfileScreenIntent {
    data class GetUser(val id: Long) : OtherUserProfileScreenIntent
}