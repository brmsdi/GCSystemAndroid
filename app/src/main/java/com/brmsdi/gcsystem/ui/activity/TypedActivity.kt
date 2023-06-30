package com.brmsdi.gcsystem.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.ui.utils.TypedAuth
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import java.util.TreeMap

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
open class TypedActivity : AppCompatActivity(), TypedAuth {
    private var _types: TreeMap<String, String> = TreeMap()

    override fun getRepositoryTypeAuth(typeAuth: String): AuthenticableRepository {
        for (type in _types) {
            if (type.key == typeAuth) {
                return get(named(type.value))
            }
        }
        throw IllegalArgumentException(getString(R.string.invalide_type_auth) + typeAuth)
    }

    override fun setTypes(types: TreeMap<String, String>) {
        _types = types
    }

    override fun getTypes(): TreeMap<String, String> = _types
}