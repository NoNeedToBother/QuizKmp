package ru.kpfu.itis.quiz.feature.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
class Result(
    val id: Long,
    val score: Double,
)
