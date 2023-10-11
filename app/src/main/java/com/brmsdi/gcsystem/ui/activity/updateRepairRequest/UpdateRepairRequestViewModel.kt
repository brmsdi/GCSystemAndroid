package com.brmsdi.gcsystem.ui.activity.updateRepairRequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
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
class UpdateRepairRequestViewModel(
    application: Application,
    private val repairRequestRepository: RepairRequestRepository
) : AndroidViewModel(application) {
    private val _repairRequest = MutableLiveData<RepairRequest>()
    val repairRequest: LiveData<RepairRequest> = _repairRequest
    private val _errorLoadData = MutableLiveData<ValidationModelDTO>()
    val errorLoadData: LiveData<ValidationModelDTO> = _errorLoadData
    private val _screenData = MutableLiveData<RepairRequestRegisterDataDTO>()
    val screenData: LiveData<RepairRequestRegisterDataDTO> = _screenData
    private val _updatedRepairRequest = MutableLiveData<RepairRequest>()
    val updatedRepairRequest: LiveData<RepairRequest> = _updatedRepairRequest
    private val _errorUpdate = MutableLiveData<ValidationModelDTO>()
    val errorUpdate: LiveData<ValidationModelDTO> = _errorUpdate
    fun getById(id: Int) {
        repairRequestRepository.getById(id, object : APIEvent<RepairRequest> {
            override fun onResponse(model: RepairRequest) {
                _repairRequest.value = model
            }

            override fun onError(response: Response<RepairRequest>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO =
                        TextUtils.jsonToObject(it, ResponseRequestDTO::class.java)
                    _errorLoadData.value = ValidationModelDTO(responseRequestDTO.errors[0].message)
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _errorLoadData.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    val message = throwable.message
                        ?: getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                    _errorLoadData.value =
                        ValidationModelDTO(message)
                }
            }
        })
    }

    fun update(repairRequest: RepairRequest) {
        repairRequestRepository.update(repairRequest, object : APIEvent<RepairRequest> {
            override fun onResponse(model: RepairRequest) {
                _updatedRepairRequest.value = model
            }

            override fun onError(response: Response<RepairRequest>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO =
                        TextUtils.jsonToObject(it, ResponseRequestDTO::class.java)
                    _errorUpdate.value = ValidationModelDTO(responseRequestDTO.errors[0].message)
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _errorUpdate.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    val message = throwable.message
                        ?: getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                    _errorUpdate.value =
                        ValidationModelDTO(message)
                }
            }
        })
    }

    fun loadDataScreenRepairRequest() {
        repairRequestRepository.loadDataNewRepairRequest(object :
            APIEvent<RepairRequestRegisterDataDTO> {
            override fun onResponse(model: RepairRequestRegisterDataDTO) {
                _screenData.value = model
            }

            override fun onError(response: Response<RepairRequestRegisterDataDTO>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO =
                        TextUtils.jsonToObject(it, ResponseRequestDTO::class.java)
                    _errorLoadData.value = ValidationModelDTO(responseRequestDTO.errors[0].message)
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _errorLoadData.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    val message = throwable.message
                        ?: getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                    _errorLoadData.value =
                        ValidationModelDTO(message)
                }
            }
        })
    }
}