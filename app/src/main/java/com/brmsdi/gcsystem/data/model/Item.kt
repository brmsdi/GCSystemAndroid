package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
@Parcelize
data class Item(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("description")
    var description: String,

    @SerializedName("quantity")
    var quantity: Int,

    @SerializedName("value")
    var value: Double
) : Parcelable