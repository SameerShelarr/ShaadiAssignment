package com.sameershelar.shaadiassignment.data.model.db

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.sameershelar.shaadiassignment.data.local.converters.*
import com.sameershelar.shaadiassignment.data.model.other.*

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true)
    val mId: Int = 0,

    val gender: String,

    val email: String,

    val phone: String,

    val cell: String,

    val nat: String,

    @Expose(deserialize = false,  serialize = false)
    val isAccepted: Boolean = false,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(NameConverter::class)
    val name: Name,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(LocationConverter::class)
    val location: Location,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(LoginConverter::class)
    val login: Login,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(DOBConverter::class)
    val dob: DOB,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(RegisteredConverter::class)
    val registered: Registered,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(IDConverter::class)
    val id: ID,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(PictureConverter::class)
    val picture: Picture,

    @ColumnInfo(
        typeAffinity = TEXT
    ) @TypeConverters(MemberSelectionConverter::class)
    @Expose(deserialize = false, serialize = false)
    var memberSelection: MemberSelection? = null
) {
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) return false

        other as Member

        if (mId == other.mId) return false
        if (gender == other.gender) return false
        if (email == other.email) return false
        if (phone == other.phone) return false
        if (cell == other.cell) return false
        if (nat == other.nat) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mId
        result = 31 * result + gender.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + cell.hashCode()
        result = 31 * result + nat.hashCode()
        result = 31 * result + isAccepted.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + dob.hashCode()
        result = 31 * result + registered.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + picture.hashCode()
        return result
    }
}
