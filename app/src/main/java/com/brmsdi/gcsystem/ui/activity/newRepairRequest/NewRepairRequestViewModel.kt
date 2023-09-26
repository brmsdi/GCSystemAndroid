package com.brmsdi.gcsystem.ui.activity.newRepairRequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class NewRepairRequestViewModel(
    application: Application,
    private val repairRequestRepository: RepairRequestRepository
) :
    AndroidViewModel(application = application) {
    private val _screenData = MutableLiveData<RepairRequestRegisterDataDTO>()
    val screenData: LiveData<RepairRequestRegisterDataDTO> = _screenData
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error
    private val _saved = MutableLiveData<ValidationModelDTO>()
    val saved: LiveData<ValidationModelDTO> = _saved
    fun save(repairRequest: RepairRequestRegisterDTO) {
        repairRequestRepository.save(repairRequest, object : APIEvent<RepairRequest> {
            override fun onResponse(model: RepairRequest) {
                _saved.value = ValidationModelDTO()
            }

            override fun onError(response: Response<RepairRequest>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO =
                        TextUtils.jsonToObject(it, ResponseRequestDTO::class.java)
                    _error.value = ValidationModelDTO(responseRequestDTO.errors[0].message)
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _error.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    _error.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }
        })
    }

    fun loadDataNewRepairRequest() {
        repairRequestRepository.loadDataNewRepairRequest(object :
            APIEvent<RepairRequestRegisterDataDTO> {
            override fun onResponse(model: RepairRequestRegisterDataDTO) {
                _screenData.value = model
            }

            override fun onError(response: Response<RepairRequestRegisterDataDTO>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO =
                        TextUtils.jsonToObject(it, ResponseRequestDTO::class.java)
                    _error.value = ValidationModelDTO(responseRequestDTO.errors[0].message)
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _error.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    _error.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }
        })
    }
}