package cn.body.pressup.databases

import androidx.room.*
import cn.body.pressup.models.BloodPress

@Dao
interface BloodPressDataDao {

    @Insert
    suspend fun insert(list: List<BloodPress>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(BloodPress: BloodPress): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(BloodPress: BloodPress): Int

    @Delete
    suspend fun delete(BloodPress: BloodPress)

    @Delete
    suspend fun delete(BloodPress: List<BloodPress>)

    @Query("DELETE FROM ${AppDatabase.T_BP} where id=:id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM ${AppDatabase.T_BP}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${AppDatabase.T_BP} where id=:id")
    fun queryById(id: Int): BloodPress?

    @Query("SELECT * FROM ${AppDatabase.T_BP}")
    suspend fun queryAll(): List<BloodPress>

}