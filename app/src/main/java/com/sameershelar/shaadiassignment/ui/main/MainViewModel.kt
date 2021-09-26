package com.sameershelar.shaadiassignment.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.data.repository.ShaadiRepository
import com.sameershelar.shaadiassignment.events.ShaadiAPIEvents
import com.sameershelar.shaadiassignment.utils.AppConstants.RESULT_MEMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ShaadiRepository
) : ViewModel() {

    //region declarations and variables

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val shaadiAPIEventsChannel = Channel<ShaadiAPIEvents>()
    val shaadiAPIEvents = shaadiAPIEventsChannel.receiveAsFlow()

    var isDataFetched: Boolean = false

    //endregion

    //region DB access methods

    fun insertMember(member: Member) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMember(member = member)
        }

    fun updateMember(member: Member) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMember(member = member)
        }

    fun getAllMembers() = repository.getAllMembers()

    suspend fun isMembersTableEmpty(): Boolean {
        val deferred = viewModelScope.async(Dispatchers.IO) {
            val member: Member? = repository.getOne()
            return@async member == null
        }
        return deferred.await()
    }

    //endregion

    //region network access methods

    fun getMembersFromRemoteServerAndSaveInDb() =
        viewModelScope.launch(Dispatchers.IO) {
            if (!isDataFetched) {
                shaadiAPIEventsChannel.send(ShaadiAPIEvents.ShaadiAPIDataLoadingStarted)
                try {
                    val response = repository.getMembersFromRemoteServer()
                    if (response.isSuccessful) {
                        response.body()?.let { jsonObject ->
                            extractMembersFromJsonAndSaveInDb(jsonObject)
                        }
                    }
                    isDataFetched = true
                } catch (e: Exception) {
                    Log.e(TAG, "getMembersFromRemoteServerAndSaveInDb: ${e.message}", e)
                    shaadiAPIEventsChannel.send(ShaadiAPIEvents.ShaadiAPIDataFetchFailed)
                }
            } else {
                Log.d(TAG, "getMembersFromRemoteServerAndSaveInDb: Data is fetched already.")
                shaadiAPIEventsChannel.send(ShaadiAPIEvents.ShaadiAPIDataLoadingFinished)
            }
        }

    //endregion

    //region helper methods

    /**
     * Extracts Member objects from the result json array and saves them into database.
     */
    private fun extractMembersFromJsonAndSaveInDb(jsonObject: JsonObject) {
        viewModelScope.launch(Dispatchers.Default) {
            val membersJsonArray = jsonObject.getAsJsonArray(RESULT_MEMBER)

            val membersList = mutableListOf<Member>()

            membersJsonArray.forEach { jsonElement ->
                membersList.add(Gson().fromJson(jsonElement, Member::class.java))
            }

            membersList.forEach { member ->
                insertMember(member)
            }
            shaadiAPIEventsChannel.send(ShaadiAPIEvents.ShaadiAPIDataLoadingFinished)
        }
    }

    //endregion
}
