package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class RepairRequest(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("problemDescription")
    var problemDescription: String,

    @SerializedName("date")
    var date: Date,

    @SerializedName("typeProblem")
    var typeProblem: TypeProblem,

    @SerializedName("lessee")
    var lessee: Lessee,

    @SerializedName("condominium")
    var condominium: Condominium,

    @SerializedName("status")
    var status: Status?,

    @SerializedName("items")
    var items: Set<Item>?,

    @SerializedName("apartmentNumber")
    var apartmentNumber: String,

    @SerializedName("orderService")
    var orderService: OrderService?) : Parcelable {}
