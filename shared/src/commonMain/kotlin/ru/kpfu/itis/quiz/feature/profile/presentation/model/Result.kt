package ru.kpfu.itis.quiz.feature.profile.presentation.model

import kotlinx.serialization.Serializable

@Serializable
class Result(
    val id: Long,
    val score: Double,
)
