package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.other.ID
import com.sameershelar.shaadiassignment.data.model.other.Picture

class PictureConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(picture: Picture): String =
            Gson().toJson(picture)

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String): Picture =
            Gson().fromJson(json, Picture::class.java)

    }
}