package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.OrderService

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class PagingOrderServiceModel(val key: Int, val page: Int, val orderService: OrderService)