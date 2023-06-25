package com.brmsdi.gcsystem.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.TokenChangePasswordDTO
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.ui.utils.TextUtils
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class NewPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val _responseRequestDTO = MutableLiveData<ResponseRequestDTO>()
    val responseRequestDTO: LiveData<ResponseRequestDTO> = _responseRequestDTO
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private var executor: Executor = Executors.newSingleThreadExecutor()

    fun save(
        tokenChangePasswordDTO: TokenChangePasswordDTO,
        authenticableRepository: AuthenticableRepository
    ) {
        executor.execute {
            authenticableRepository.changePassword(
                tokenChangePasswordDTO,
                object : APIEventStringAndJSON {
                    override fun onSuccess() {
                        val responseRequestDTO = ResponseRequestDTO()
                        responseRequestDTO.status = HttpsURLConnection.HTTP_OK
                        _responseRequestDTO.postValue(responseRequestDTO)
                    }

                    override fun onError(errorString: String) {
                        _responseRequestDTO.postValue(
                            TextUtils.jsonToObject(
                                errorString,
                                ResponseRequestDTO::class.java
                            )
                        )
                    }

                    override fun onConnectFailure() {
                        _errorMessage.postValue(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                    }
                })
        }

    }
}