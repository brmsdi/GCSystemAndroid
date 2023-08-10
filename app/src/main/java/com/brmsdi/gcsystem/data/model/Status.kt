package com.brmsdi.gcsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(@SerializedName("id") var id: Int, @SerializedName("name") var name: String) :
    Parcelable