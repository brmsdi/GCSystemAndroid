package com.brmsdi.gcsystem.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.TOKEN
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.remote.retrofit.RetrofitClient
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.data.repositories.impl.EmployeeRepositoryImpl
import com.brmsdi.gcsystem.data.repositories.impl.LesseeRepositoryImpl
import com.brmsdi.gcsystem.data.security.SecurityPreferences
import com.brmsdi.gcsystem.ui.utils.Token
import com.brmsdi.gcsystem.ui.utils.ValidationModel
import retrofit2.Response
import java.net.ConnectException

class LoginViewModel(application : Application) : AndroidViewModel(application) {
    private val _login = MutableLiveData<ValidationModel>()
    val login : LiveData<ValidationModel> = _login
    private val securityPreferences = SecurityPreferences(application.applicationContext)

    /*
    @Deprecated("Liskov")
    fun authenticate(cpf: String, password: String, typeAuth: String) {
        if (typeAuth == this.getApplication<Application>().getString(R.string.employee))
        {
            loginRepository.authenticateEmployee(cpf, password, object : APIEvent<Token> {
                override fun onResponse(model: Token) {
                    securityPreferences.story(TOKEN, model.token)
                    RetrofitClient.addToken(model.token)
                    _login.value = ValidationModel()
                }

                override fun onError(response: Response<Token>) {
                    if (response.code() == 401) {
                        _login.value = ValidationModel(getApplication<Application>().getString(R.string.login_error))
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    val cause =  throwable.cause
                    if (cause is ConnectException) {
                        _login.value = ValidationModel(getApplication<Application>().getString(R.string.ERROR_CONNECTION) )
                    } else {
                        _login.value = ValidationModel(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED) )
                    }
                }
            })
        } else if (typeAuth == this.getApplication<Application>().getString(R.string.lessee)) {
            loginRepository.authenticateLessee(cpf, password, object : APIEvent<Token> {
                override fun onResponse(model: Token) {
                    securityPreferences.story(TOKEN, model.token)
                    RetrofitClient.addToken(model.token)
                    _login.value = ValidationModel()
                }

                override fun onError(response: Response<Token>) {
                    if (response.code() == 401) {
                        _login.value = ValidationModel(getApplication<Application>().getString(R.string.login_error))
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    val cause =  throwable.cause
                    if (cause is ConnectException) {
                        _login.value = ValidationModel(getApplication<Application>().getString(R.string.ERROR_CONNECTION) )
                    } else {
                        _login.value = ValidationModel(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED) )
                    }
                }
            })
        }
    } */

    fun authenticate(cpf: String, password: String, typeAuth: String) {
        if (typeAuth == this.getApplication<Application>().getString(R.string.employee))
        {
            authenticate(cpf, password, EmployeeRepositoryImpl())
        } else if (typeAuth == this.getApplication<Application>().getString(R.string.lessee)) {
            authenticate(cpf, password, LesseeRepositoryImpl())
        }
    }

    private fun authenticate(cpf: String, password: String, authenticableRepository : AuthenticableRepository) {
        authenticableRepository.authenticate(cpf, password, object : APIEvent<Token> {
            override fun onResponse(model: Token) {
                securityPreferences.story(TOKEN, model.token)
                RetrofitClient.addToken(model.token)
                _login.value = ValidationModel()
            }

            override fun onError(response: Response<Token>) {
                if (response.code() == 401) {
                    _login.value = ValidationModel(getApplication<Application>().getString(R.string.login_error))
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause =  throwable.cause
                if (cause is ConnectException) {
                    _login.value = ValidationModel(getApplication<Application>().getString(R.string.ERROR_CONNECTION) )
                } else {
                    _login.value = ValidationModel(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED) )
                }
            }
        })
    }
    fun verifyAuthentication() {
        val token = securityPreferences.get(TOKEN)
        if (token.isNotEmpty()) {
            RetrofitClient.addToken(token)
        }
    }
}