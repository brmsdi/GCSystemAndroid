package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Condominium

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class CondominiumSpinnerDTO(private val condominium: Condominium) : SpinnerDTO<Condominium> {
    override fun getModel() : Condominium {
        return  condominium
    }
    override fun toString(): String {
        return condominium.name
    }
}