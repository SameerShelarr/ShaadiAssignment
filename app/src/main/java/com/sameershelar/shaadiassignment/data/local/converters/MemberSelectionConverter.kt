package com.sameershelar.shaadiassignment.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sameershelar.shaadiassignment.data.model.other.MemberSelection

class MemberSelectionConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromObjectToJson(memberSelection: MemberSelection?): String? =
            if (memberSelection != null) {
                Gson().toJson(memberSelection)
            } else {
                null;
            }

        @JvmStatic
        @TypeConverter
        fun fromJsonToObject(json: String?): MemberSelection? =
            if (json != null) {
                Gson().fromJson(json, MemberSelection::class.java)
            } else {
                null
            }

    }
}