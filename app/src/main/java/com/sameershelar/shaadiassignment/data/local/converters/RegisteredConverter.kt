package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.other.DOB
import com.sameershelar.shaadiassignment.data.model.other.Registered

class RegisteredConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(registered: Registered): String =
            Gson().toJson(registered)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): Registered =
            Gson().fromJson(json, Registered::class.java)

    }
}