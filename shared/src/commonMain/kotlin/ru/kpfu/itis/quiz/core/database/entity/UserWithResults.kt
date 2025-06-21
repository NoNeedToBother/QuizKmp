package ru.kpfu.itis.quiz.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithResults(
    @Embedded val user: UserEntity,
    @Relation(parentColumn = "id", entityColumn = "user_id")
    val results: List<ResultEntity>
)
