package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.other.Location
import com.sameershelar.shaadiassignment.data.model.other.Login

class LoginConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(login: Login): String =
            Gson().toJson(login)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): Login =
            Gson().fromJson(json, Login::class.java)

    }
}