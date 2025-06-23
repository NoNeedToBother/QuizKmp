package ru.kpfu.itis.quiz.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.kpfu.itis.quiz.core.database.entity.ResultAndUser
import ru.kpfu.itis.quiz.core.database.entity.ResultEntity

@Dao
interface ResultDao {

    @Insert
    fun save(result: ResultEntity)

    @Query("select * from results")
    @Transaction
    fun getAll(): List<ResultAndUser>

}
