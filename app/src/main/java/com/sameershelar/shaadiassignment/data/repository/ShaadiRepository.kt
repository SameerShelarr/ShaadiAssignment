package com.sameershelar.shaadiassignment.data.repository

import androidx.lifecycle.LiveData
import com.sameershelar.shaadiassignment.data.local.dao.MemberDao
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.data.remote.api.ShaadiAPI
import javax.inject.Inject

class ShaadiRepository @Inject constructor(
    private val memberDao: MemberDao,
    private val shaadiAPI: ShaadiAPI,
) {
    //region DB access methods

    suspend fun insertMember(member: Member) = memberDao.insert(member)

    suspend fun updateMember(member: Member) = memberDao.update(member)

    fun getAllMembers(): LiveData<List<Member>> = memberDao.getAll()

    suspend fun deleteAllMembers() = memberDao.deleteAll()

    suspend fun getOne() = memberDao.getOne()

    //endregion

    //region server access methods

    suspend fun getMembersFromRemoteServer() = shaadiAPI.getMembersFromRemoteServer()

    //endregion
}