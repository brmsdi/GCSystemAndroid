package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.TypeProblem


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class TypeProblemSpinnerDTO(private val typeProblem: TypeProblem) : SpinnerDTO<TypeProblem> {

    override fun getModel(): TypeProblem {
        return typeProblem
    }

    override fun toString(): String {
        return typeProblem.name
    }
}