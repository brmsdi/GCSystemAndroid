package com.brmsdi.gcsystem.ui.fragment.sendEmail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.jsonToObject
import retrofit2.Response
import java.net.ConnectException
import javax.net.ssl.HttpsURLConnection

class SendEmailViewModel(application: Application) : AndroidViewModel(application) {
    private val _responseRequestDTO = MutableLiveData<ResponseRequestDTO>()
    val responseRequestDTO: LiveData<ResponseRequestDTO> = _responseRequestDTO
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun requestCode(email: String, authenticableRepository: AuthenticableRepository) {
        authenticableRepository.requestCode(email, object : APIEvent<ResponseDTO> {
            override fun onResponse(model: ResponseDTO) {
                val responseRequestDTO = ResponseRequestDTO()
                responseRequestDTO.status = HttpsURLConnection.HTTP_OK
                _responseRequestDTO.postValue(responseRequestDTO)
            }

            override fun onError(response: Response<ResponseDTO>) {
                response.errorBody()?.string()?.let {
                    _responseRequestDTO.postValue(
                        jsonToObject(
                            it,
                            ResponseRequestDTO::class.java
                        )
                    )
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _errorMessage.value =
                        getApplication<Application>().getString(R.string.ERROR_CONNECTION)
                } else {
                    _errorMessage.value =
                        getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                }
            }
        })
    }
}