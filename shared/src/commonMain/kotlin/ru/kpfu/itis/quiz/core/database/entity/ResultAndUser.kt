package ru.kpfu.itis.quiz.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ResultAndUser(
    @Embedded
    val result: ResultEntity,

    @Relation(parentColumn = "user_id", entityColumn = "id")
    val user: UserEntity
)