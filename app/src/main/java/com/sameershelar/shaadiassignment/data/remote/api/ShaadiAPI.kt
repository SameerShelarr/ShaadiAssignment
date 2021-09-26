package com.sameershelar.shaadiassignment.data.remote.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface ShaadiAPI {

    @GET("api/?results=10")
    suspend fun getMembersFromRemoteServer(): Response<JsonObject>
}