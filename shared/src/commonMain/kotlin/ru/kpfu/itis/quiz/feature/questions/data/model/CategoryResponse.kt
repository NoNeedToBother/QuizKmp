package ru.kpfu.itis.quiz.feature.questions.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryResponse(
    @SerialName("trivia_categories") val info: List<CategoryInfoResponse>
)

@Serializable
internal data class CategoryInfoResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)
