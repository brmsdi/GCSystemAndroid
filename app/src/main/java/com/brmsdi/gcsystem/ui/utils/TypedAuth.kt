package com.brmsdi.gcsystem.ui.utils

import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import java.util.TreeMap

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface TypedAuth {
    fun getRepositoryTypeAuth(typeAuth: String): AuthenticableRepository

    fun setTypes(types: TreeMap<String, String>)

    fun getTypes(): TreeMap<String, String>
}