package com.brmsdi.gcsystem.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.ui.utils.ResponseRequest
import com.brmsdi.gcsystem.ui.utils.TextUtils
import javax.net.ssl.HttpsURLConnection

class NewPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val _responseRequest = MutableLiveData<ResponseRequest>()
    val responseRequest: LiveData<ResponseRequest> = _responseRequest

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun changePassword(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        authenticableRepository: AuthenticableRepository
    ) {
        authenticableRepository.changePassword(
            tokenChangePasswordDTO,
            object : APIEventStringAndJSON {
                override fun onSuccess() {
                    val responseRequest = ResponseRequest()
                    responseRequest.status = HttpsURLConnection.HTTP_OK
                    _responseRequest.postValue(responseRequest)
                }

                override fun onError(errorString: String) {
                    _responseRequest.postValue(
                        TextUtils.jsonToObject(
                            errorString,
                            ResponseRequest::class.java
                        )
                    )
                }

                override fun onConnectFailure() {
                    _errorMessage.postValue(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                }
            })
    }
}