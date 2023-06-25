package com.brmsdi.gcsystem.ui.activities

import androidx.appcompat.app.AppCompatActivity
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.ui.utils.TypedAuth
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
open class TypedActivity : AppCompatActivity(), TypedAuth {
    private var _types: Map<String, String> = emptyMap()

    override fun getRepositoryTypeAuth(typeAuth: String): AuthenticableRepository {
        for (type in _types) {
            if (type.key == typeAuth) {
                return get(named(type.value))
            }
        }
        throw IllegalArgumentException(getString(R.string.invalide_type_auth) + typeAuth)
    }

    override fun setTypes(types: Map<String, String>) {
        _types = types
    }

    override fun getTypes(): Map<String, String> = _types
}