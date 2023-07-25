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
data class TypeProblem(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
) : Parcelable {}