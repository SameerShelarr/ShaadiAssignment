package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.data.model.other.Location

class LocationConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(location: Location): String =
            Gson().toJson(location)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): Location =
            Gson().fromJson(json, Location::class.java)

    }
}