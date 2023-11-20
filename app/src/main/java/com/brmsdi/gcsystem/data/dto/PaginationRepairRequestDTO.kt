package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.RepairRequest
import com.google.gson.annotations.SerializedName

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PaginationRepairRequestDTO(
    @SerializedName("content")
    var content: MutableList<RepairRequest>,
    totalElements: Int,
    totalPages: Int,
    last: Boolean,
    first: Boolean,
    size: Int,
    number: Int,
    numberOfElements: Int,
    empty: Boolean
) : PaginationDTO(
    totalElements,
    totalPages,
    last,
    first,
    size,
    number,
    numberOfElements,
    empty
)
