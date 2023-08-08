package com.brmsdi.gcsystem.ui.activity.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.TOKEN
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.data.security.SecurityPreferences
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import retrofit2.Response
import java.net.ConnectException

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _login = MutableLiveData<ValidationModelDTO>()
    val login: LiveData<ValidationModelDTO> = _login
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    fun authenticate(
        cpf: String,
        password: String,
        authenticableRepository: AuthenticableRepository
    ) {
        authenticableRepository.authenticate(cpf, password, object : APIEvent<TokenDTO> {
            override fun onResponse(model: TokenDTO) {
                addAuth(model.token)
                _login.value = ValidationModelDTO()
            }

            override fun onError(response: Response<TokenDTO>) {
                if (response.code() == 401) {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.login_error))
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }
        })
    }

    private fun addAuth(token: String) {
        securityPreferences.story(TOKEN, token)
        RetrofitClient.addToken(token)
    }

    fun verifyAuthentication(): Boolean {
        val token = securityPreferences.get(TOKEN)
        return if (token.isNotEmpty()) {
            RetrofitClient.addToken(token)
            true
        } else false
    }
}