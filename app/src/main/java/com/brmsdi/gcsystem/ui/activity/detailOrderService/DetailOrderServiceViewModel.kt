package com.brmsdi.gcsystem.ui.activity.detailOrderService

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.data.repository.OrderServiceRepository
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class DetailOrderServiceViewModel(
    application: Application,
    private val orderServiceRepository: OrderServiceRepository,
    private val repairRequestRepository: RepairRequestRepository
) : AndroidViewModel(application) {
    private val _orderService = MutableLiveData<OrderService>()
    val orderService: LiveData<OrderService> = _orderService
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error
    fun details(id: Int) {
        orderServiceRepository.details(id, object : APIEvent<OrderService> {
            override fun onResponse(model: OrderService) {
                _orderService.value = model
            }

            override fun onError(response: Response<OrderService>) {
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
        })
    }
}