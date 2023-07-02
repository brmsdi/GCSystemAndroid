package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Employee(
    @SerializedName("name")
    var name: String
) : Parcelable {

    override fun toString(): String {
        return this.name
    }
}
