package ru.kpfu.itis.quiz.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import ru.kpfu.itis.quiz.core.database.entity.ResultEntity

@Dao
interface ResultDao {

    @Insert
    fun save(result: ResultEntity)

}
