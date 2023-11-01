package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.pagingsource.PagingModel
import com.google.gson.annotations.SerializedName


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
open class PaginationDTO<T : PagingModel<T>>(
    @SerializedName("content")
    var content: MutableList<T>,
    @SerializedName("totalElements")
    var totalElements: Int,
    @SerializedName("totalPages")
    var totalPages: Int,
    @SerializedName("last")
    var last: Boolean,
    @SerializedName("first")
    var first: Boolean,
    @SerializedName("size")
    var size: Int,
    @SerializedName("number")
    var number: Int,
    @SerializedName("numberOfElements")
    var numberOfElements: Int,
    @SerializedName("empty")
    var empty: Boolean
)