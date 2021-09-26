package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.data.model.other.Name

class NameConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(name: Name): String =
            Gson().toJson(name)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): Name =
            Gson().fromJson(json, Name::class.java)

    }
}