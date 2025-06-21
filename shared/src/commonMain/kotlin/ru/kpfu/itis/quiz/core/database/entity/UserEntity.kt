package ru.kpfu.itis.quiz.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val profilePictureUrl: String,
    val info: String,
    val dateRegistered: String,
)
