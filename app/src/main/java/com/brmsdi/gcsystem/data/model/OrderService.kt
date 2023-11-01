package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.brmsdi.gcsystem.data.pagingsource.PagingModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

@Parcelize
data class OrderService(
    @SerializedName("id")
    var id: Int,

    @SerializedName("generationDate")
    var generationDate: Date,

    @SerializedName("reservedDate")
    var reservedDate: Date?,

    @SerializedName("completionDate")
    var completionDate: Date?,

    @SerializedName("repairRequests")
    var repairRequests: MutableList<RepairRequest>,

    @SerializedName("employees")
    var employees: Set<Employee>,

    @SerializedName("status")
    var status: Status
) : Parcelable, PagingModel<OrderService> {
    override fun getPagingID() : Int = id
    override fun getModel(): OrderService = this
}