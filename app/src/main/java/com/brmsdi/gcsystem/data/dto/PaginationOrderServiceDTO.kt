package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.OrderService

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PaginationOrderServiceDTO(
    content: MutableList<OrderService>,
    totalElements: Int,
    totalPages: Int,
    last: Boolean,
    first: Boolean,
    size: Int,
    number: Int,
    numberOfElements: Int,
    empty: Boolean
) : PaginationDTO<OrderService>(
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
