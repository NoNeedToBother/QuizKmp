package ru.kpfu.itis.quiz.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.kpfu.itis.quiz.core.database.entity.UserEntity
import ru.kpfu.itis.quiz.core.database.entity.UserWithResults

@Dao
interface UserDao {

    @Insert
    suspend fun save(user: UserEntity): Long

    @Update
    suspend fun update(user: UserEntity)

    @Query("select * from users where username like :query limit :limit offset :offset")
    fun getByQueryNameWithPaging(query: String, limit: Int, offset: Int): List<UserEntity>

    @Query("select * from users")
    @Transaction
    fun getAllWithResults(): List<UserWithResults>

    @Query("select * from users where username=:username and password=:password")
    fun getByUsernameAndPassword(username: String, password: String): UserEntity?

    @Query("select * from users where id=:id")
    fun getById(id: Long): UserEntity?

    @Query("select * from users where signed_in=true")
    fun getSignedInUser(): UserEntity?

    @Query("select * from users where signed_in=true")
    @Transaction
    fun getSignedInUserWithResults(): UserWithResults?

    @Query("select * from users where id=:id")
    @Transaction
    fun getByIdWithResults(id: Long): UserWithResults?

    @Query("update users set password=:password where id=:id")
    fun updatePassword(id: Long, password: String)

    @Query("update users set signed_in=false")
    fun logout()

}
