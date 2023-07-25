package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condominium(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("numberApartments")
    var numberApartments: Int,

    @SerializedName("status")
    var status: Status,

    @SerializedName("localization")
    var localization: LocalizationCondominium
) : Parcelable {}
