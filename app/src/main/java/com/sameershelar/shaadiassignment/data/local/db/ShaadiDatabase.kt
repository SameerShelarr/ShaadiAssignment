package com.sameershelar.shaadiassignment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sameershelar.shaadiassignment.data.local.converters.*
import com.sameershelar.shaadiassignment.data.local.dao.MemberDao
import com.sameershelar.shaadiassignment.data.model.db.Member
import com.sameershelar.shaadiassignment.data.model.other.Name

@Database(
    entities = [Member::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    NameConverter::class,
    LocationConverter::class,
    LoginConverter::class,
    DOBConverter::class,
    RegisteredConverter::class,
    IDConverter::class,
    PictureConverter::class,
    MemberSelectionConverter::class,
)
abstract class ShaadiDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
}