package com.brmsdi.gcsystem.ui.fragment.debt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.PaginationDebtDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.repository.DebtRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException

class DebtViewModel(application: Application, private val debtRepository: DebtRepository) : AndroidViewModel(application) {
    private val _debts = MutableLiveData<PaginationDebtDTO>()
    val debts : LiveData<PaginationDebtDTO> = _debts
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error

    fun loadDebts(params: Map<String, String> = mapOf()) {
        debtRepository.loadDebts(params, object : APIEvent<PaginationDebtDTO> {
            override fun onResponse(model: PaginationDebtDTO) {
                _debts.value = model
            }

            override fun onError(response: Response<PaginationDebtDTO>) {
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