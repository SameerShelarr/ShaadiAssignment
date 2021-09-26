package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.other.DOB
import com.sameershelar.shaadiassignment.data.model.other.Login

class DOBConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(dob: DOB): String =
            Gson().toJson(dob)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): DOB =
            Gson().fromJson(json, DOB::class.java)

    }
}