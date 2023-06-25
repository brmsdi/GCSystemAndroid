package com.brmsdi.gcsystem.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.data.dto.ChangePasswordDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.jsonToObject
import com.brmsdi.gcsystem.data.dto.TokenDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelWithTokenDTO
import retrofit2.Response
import java.net.ConnectException

class SendCodeViewModel(application: Application) : AndroidViewModel(application) {
    private val _validateModel = MutableLiveData<ValidationModelWithTokenDTO>()
    val validateModel: LiveData<ValidationModelWithTokenDTO> = _validateModel

    fun sendCode(
        changePasswordDataDTO: ChangePasswordDataDTO,
        authenticableRepository: AuthenticableRepository
    ) {
        authenticableRepository.sendCode(changePasswordDataDTO, object : APIEvent<TokenDTO> {
            override fun onResponse(model: TokenDTO) {
                _validateModel.value = ValidationModelWithTokenDTO(tokenDTO = model)
            }

            override fun onError(response: Response<TokenDTO>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO = jsonToObject(it, ResponseRequestDTO::class.java)
                    _validateModel.value = ValidationModelWithTokenDTO(
                        null,
                        responseRequestDTO.errors[0].message
                    )
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _validateModel.value = ValidationModelWithTokenDTO(
                        null,
                        getApplication<Application>().getString(R.string.ERROR_CONNECTION)
                    )
                } else {
                    _validateModel.value = ValidationModelWithTokenDTO(
                        null,
                        getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                    )
                }
            }
        })
    }
}