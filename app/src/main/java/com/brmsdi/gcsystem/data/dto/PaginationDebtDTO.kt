package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Debt

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PaginationDebtDTO(
    content: MutableList<Debt>,
    totalElements: Int,
    totalPages: Int,
    last: Boolean,
    first: Boolean,
    size: Int,
    number: Int,
    numberOfElements: Int,
    empty: Boolean
) : PaginationDTO<Debt>(
    content,
    totalElements,
    totalPages,
    last,
    first,
    size,
    number,
    numberOfElements,
    empty
)
