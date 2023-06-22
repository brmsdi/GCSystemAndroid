package com.brmsdi.gcsystem.ui.activities

import androidx.appcompat.app.AppCompatActivity
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.ui.utils.AuthType
import com.brmsdi.gcsystem.ui.utils.TypedAuth
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
open class TypedActivity : AppCompatActivity(), TypedAuth {
    override fun getRepositoryTypeAuth(typeAuth: String): AuthenticableRepository {
        return when (typeAuth) {
            getString(R.string.employee) -> get(named(AuthType.EMPLOYEE.type))
            getString(R.string.lessee) -> get(named(AuthType.LESSEE.type))
            else -> throw IllegalArgumentException(getString(R.string.invalide_type_auth) + typeAuth)
        }
    }
}