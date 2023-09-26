package com.brmsdi.gcsystem.data.dto

import com.google.gson.annotations.SerializedName

data class RepairRequestRegisterDTO(
    @SerializedName("problemDescription")
    val problemDescription: String,
    @SerializedName("condominiumID")
    val condominiumID: Int,
    @SerializedName("apartmentNumber")
    val apartmentNumber: String,
    @SerializedName("typeProblemID")
    val typeProblemID: Int,
)
