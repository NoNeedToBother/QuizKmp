package ru.kpfu.itis.quiz.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.kpfu.itis.quiz.core.database.entity.UserEntity
import ru.kpfu.itis.quiz.core.database.entity.UserWithResults

@Dao
interface UserDao {

    @Insert
    suspend fun save(user: UserEntity): Long

    @Query("select * from questions")
    @Transaction
    abstract fun getWithResults(): List<UserWithResults>
}
