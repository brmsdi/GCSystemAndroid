package com.brmsdi.gcsystem.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.ui.utils.ChangePasswordData
import com.brmsdi.gcsystem.ui.utils.ResponseRequest
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.jsonToObject
import com.brmsdi.gcsystem.ui.utils.Token
import com.brmsdi.gcsystem.ui.utils.ValidationModelWithToken
import retrofit2.Response
import java.net.ConnectException

class SendCodeViewModel(application: Application) : AndroidViewModel(application) {
    private val _validateModel = MutableLiveData<ValidationModelWithToken>()
    val validateModel: LiveData<ValidationModelWithToken> = _validateModel

    fun sendCode(
        changePasswordData: ChangePasswordData,
        authenticableRepository: AuthenticableRepository
    ) {
        authenticableRepository.sendCode(changePasswordData, object : APIEvent<Token> {
            override fun onResponse(model: Token) {
                _validateModel.value = ValidationModelWithToken(token = model)
            }

            override fun onError(response: Response<Token>) {
                response.errorBody()?.string()?.let {
                    val responseRequest = jsonToObject(it, ResponseRequest::class.java)
                    _validateModel.value = ValidationModelWithToken(
                        null,
                        responseRequest.errors[0].message
                    )
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _validateModel.value = ValidationModelWithToken(
                        null,
                        getApplication<Application>().getString(R.string.ERROR_CONNECTION)
                    )
                } else {
                    _validateModel.value = ValidationModelWithToken(
                        null,
                        getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                    )
                }
            }
        })
    }
}