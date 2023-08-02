package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

@Parcelize
data class Debt(
    @SerializedName("id")
    var id: Int,
    @SerializedName("dueDate")
    var dueDate: Date,
    @SerializedName("value")
    var value: Double,
    @SerializedName("status")
    var status: Status
) : Parcelable{}