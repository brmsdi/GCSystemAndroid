package com.brmsdi.gcsystem.data.pagingsource


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface PagingModel<E> {

    fun getPagingID() : Int

    fun getModel() : E
}