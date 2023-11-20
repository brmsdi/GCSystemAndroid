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
data class Contract(
    @SerializedName("id")
    var id: Int,
    @SerializedName("contractDate")
    var contractDate: Date,
    @SerializedName("contractValue")
    var contractValue: Double,
    @SerializedName("monthlyPaymentDate")
    var monthlyPaymentDate: Int,
    @SerializedName("monthlyDueDate")
    var monthlyDueDate: Int,
    @SerializedName("contractExpirationDate")
    var contractExpirationDate: Date,
    @SerializedName("apartmentNumber")
    var apartmentNumber: Int,
    @SerializedName("status")
    var status: Status,
    @SerializedName("condominium")
    var condominium: Condominium,
    @SerializedName("lessee")
    var lessee: Lessee,
) : Parcelable, PagingModel {
    override fun getPagingID() : Int = id
}