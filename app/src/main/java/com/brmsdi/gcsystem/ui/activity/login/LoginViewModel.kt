package com.brmsdi.gcsystem.ui.activity.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.FINGERPRINT
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.TOKEN
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.TYPE_AUTH
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.data.security.SecurityPreferences
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.UserAuthenticatedDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import retrofit2.Response
import java.net.ConnectException

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _login = MutableLiveData<ValidationModelDTO>()
    val login: LiveData<ValidationModelDTO> = _login
    private val securityPreferences = SecurityPreferences(application.applicationContext)
    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    fun authenticate(
        cpf: String,
        password: String,
        typeAuth: String,
        authenticableRepository: AuthenticableRepository
    ) {
        authenticableRepository.authenticate(cpf, password, typeAuth, object : APIEvent<TokenDTO> {
            override fun onResponse(model: TokenDTO) {
                addAuth(model.token, typeAuth)
                _login.value = ValidationModelDTO()
            }

            override fun onError(response: Response<TokenDTO>) {
                removeAuth()
                if (response.code() == 401) {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.login_error))
                } else {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    removeAuth()
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }
        })
    }

    fun verifyTokenAuthentication(authenticableRepository: AuthenticableRepository) {
        val token = securityPreferences.get(TOKEN)
        if (token.isEmpty()) {
            _isAuthenticated.value = false
            return
        }
        RetrofitClient.addToken(token)
        authenticableRepository.verifyToken(object : APIEvent<UserAuthenticatedDTO> {
            override fun onResponse(model: UserAuthenticatedDTO) {
                _isAuthenticated.value = true
            }

            override fun onError(response: Response<UserAuthenticatedDTO>) {
                removeAuth()
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    removeAuth()
                    _login.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }
        })
    }

    private fun addAuth(token: String, typeAuth: String) {
        securityPreferences.story(TOKEN, token)
        securityPreferences.story(TYPE_AUTH, typeAuth)
        RetrofitClient.addToken(token)
    }

    fun removeAuth() {
        securityPreferences.remove(TOKEN)
        securityPreferences.remove(TYPE_AUTH)
        securityPreferences.remove(FINGERPRINT)
        RetrofitClient.removeToken()
    }
}