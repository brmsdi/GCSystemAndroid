package com.brmsdi.gcsystem.data.listeners

import com.brmsdi.gcsystem.data.dto.ResponseDTO


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface PostDeletedItemEvent {
    fun post(responseDTO: ResponseDTO)
}