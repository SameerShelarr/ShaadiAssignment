package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.other.ID

class IDConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(id: ID): String =
            Gson().toJson(id)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): ID =
            Gson().fromJson(json, ID::class.java)

    }
}