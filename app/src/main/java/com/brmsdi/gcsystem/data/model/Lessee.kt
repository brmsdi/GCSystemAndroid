package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Lessee(
    @SerializedName("name")
    var name: String,
    @SerializedName("debts")
    var debts: MutableList<Debt>
) : Parcelable {}
