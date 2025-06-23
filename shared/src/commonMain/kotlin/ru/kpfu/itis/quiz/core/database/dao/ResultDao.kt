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

    @Query("select * from results where difficulty=:difficulty and category=:category and game_mode=:gameMode" +
            " limit :limit offset :offset")
    @Transaction
    fun getByGameModeDifficultyAndCategoryWithPaging(
        gameMode: String, difficulty: String, category: String,
        limit: Int, offset: Int
    ): List<ResultAndUser>

    @Query("select * from results where difficulty=:difficulty and game_mode=:gameMode" +
            " limit :limit offset :offset")
    @Transaction
    fun getByGameModeAndDifficultyWithPaging(
        gameMode: String, difficulty: String,
        limit: Int, offset: Int
    ): List<ResultAndUser>

    @Query("select * from results where category=:category and game_mode=:gameMode" +
            " limit :limit offset :offset")
    @Transaction
    fun getByGameModeAndCategoryWithPaging(
        gameMode: String, category: String,
        limit: Int, offset: Int
    ): List<ResultAndUser>

    @Query("select * from results where game_mode=:gameMode" +
            " limit :limit offset :offset")
    @Transaction
    fun getByGameModeWithPaging(
        gameMode: String, limit: Int, offset: Int
    ): List<ResultAndUser>

}
