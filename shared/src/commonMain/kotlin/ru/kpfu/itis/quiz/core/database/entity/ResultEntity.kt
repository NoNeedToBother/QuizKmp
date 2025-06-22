package ru.kpfu.itis.quiz.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "results",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: Int,
    val correct: Int,
    val total: Int,
    val difficulty: String,
    val category: String,
    @ColumnInfo(name = "game_mode")
    val gameMode: String,
    val score: Double,
    @ColumnInfo(name = "user_id")
    val userId: Long,
)
