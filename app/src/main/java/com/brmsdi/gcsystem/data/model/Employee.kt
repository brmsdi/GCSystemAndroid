package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Employee(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("cpf")
    var cpf: String,
    @SerializedName("birthDate")
    var birthDate: Date,
    @SerializedName("email")
    var email: String,
    @SerializedName("role")
    var role: Role,
    @SerializedName("hiringDate")
    var hiringDate: Date
) : Parcelable