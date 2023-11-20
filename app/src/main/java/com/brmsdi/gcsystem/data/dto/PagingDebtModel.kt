package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Debt

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class PagingDebtModel(val key: Int, val page: Int, val debt: Debt)