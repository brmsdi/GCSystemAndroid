package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Contract

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class PagingContractModel(val key: Int, val page: Int, val contract: Contract)