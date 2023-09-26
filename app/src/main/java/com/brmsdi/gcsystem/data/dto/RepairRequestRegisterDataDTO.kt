package com.brmsdi.gcsystem.data.dto

import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.TypeProblem
import com.google.gson.annotations.SerializedName

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
data class RepairRequestRegisterDataDTO(
    @SerializedName("condominiums")
    val condominiums: MutableList<Condominium>,
    @SerializedName("typeProblems")
    val typeProblems: MutableList<TypeProblem>
)