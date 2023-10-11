package com.brmsdi.gcsystem.ui.fragment.orderService

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.PaginationOrderServiceDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.repository.OrderServiceRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException

class OrderServiceViewModel(application: Application, private val orderServiceRepository: OrderServiceRepository) : AndroidViewModel(application) {
    private val _pagination = MutableLiveData<PaginationOrderServiceDTO>()
    val pagination: LiveData<PaginationOrderServiceDTO> = _pagination
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error
    fun loadOrderServices(params: Map<String, String>) {
        load(params = params)
    }

    fun search(params: Map<String, String>) {
        load(params = params, isSearch = true)
    }

    private fun load(params: Map<String, String>, isSearch: Boolean = false) {
        val event = object : APIEvent<PaginationOrderServiceDTO> {
            override fun onResponse(model: PaginationOrderServiceDTO) {
                _pagination.value = model
            }

            override fun onError(response: Response<PaginationOrderServiceDTO>) {
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
                    val message = throwable.message ?: getApplication<Application>().getString(R.string.ERROR_UNEXPECTED)
                    _error.value =
                        ValidationModelDTO(message)
                }
            }
        }

        if (isSearch) {
            orderServiceRepository.search(params, event)
            return
        }
        orderServiceRepository.loadOrderServices(params, event)
    }
}