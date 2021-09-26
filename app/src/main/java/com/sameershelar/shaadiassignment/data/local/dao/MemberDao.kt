package com.sameershelar.shaadiassignment.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sameershelar.shaadiassignment.data.model.db.Member

@Dao
interface MemberDao {

    @Insert
    suspend fun insert(member: Member)

    @Update
    suspend fun update(member: Member)

    @Query("SELECT * FROM member ORDER BY id DESC")
    fun getAll(): LiveData<List<Member>>

    @Query("SELECT * FROM member ORDER BY id LIMIT 1")
    suspend fun getOne(): Member?

    @Query("DELETE FROM member")
    suspend fun deleteAll()
}