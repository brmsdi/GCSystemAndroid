package com.brmsdi.gcsystem.ui.utils

import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface TypedAuth {
    fun getRepositoryTypeAuth(typeAuth: String): AuthenticableRepository

    fun setTypes(types: Map<String, String>)
}