package com.brmsdi.gcsystem.ui.fragment.contract

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.PaginationContractDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.repository.ContractRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ContractViewModel(
    application: Application,
    private val contractRepository: ContractRepository
) : AndroidViewModel(application) {
    private val _contracts = MutableLiveData<PaginationContractDTO>()
    val contracts: LiveData<PaginationContractDTO> = _contracts
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error
    fun loadContract(params: Map<String, String> = mapOf()) {
        contractRepository.loadContracts(params, object : APIEvent<PaginationContractDTO> {
            override fun onResponse(model: PaginationContractDTO) {
                _contracts.value = model
            }

            override fun onError(response: Response<PaginationContractDTO>) {
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