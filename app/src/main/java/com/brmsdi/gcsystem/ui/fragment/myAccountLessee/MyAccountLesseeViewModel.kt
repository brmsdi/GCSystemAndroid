package com.brmsdi.gcsystem.ui.fragment.myAccountLessee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.data.repository.LesseeRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException

class MyAccountLesseeViewModel(application: Application) : AndroidViewModel(application) {
    private val _account = MutableLiveData<Lessee>()
    val account: LiveData<Lessee> = _account
    private val _validation = MutableLiveData<ValidationModelDTO>()
    val validation: LiveData<ValidationModelDTO> = _validation

    fun getDataAccount(employeeRepository: LesseeRepository) {
        employeeRepository.myAccount(object : APIEvent<Lessee> {
            override fun onResponse(model: Lessee) {
                _account.value = model
            }

            override fun onError(response: Response<Lessee>) {
                response.errorBody()?.string()?.let {
                    val responseRequestDTO =
                        TextUtils.jsonToObject(it, ResponseRequestDTO::class.java)
                    _validation.value = ValidationModelDTO(responseRequestDTO.errors[0].message)
                }
            }

            override fun onFailure(throwable: Throwable) {
                val cause = throwable.cause
                if (cause is ConnectException) {
                    _validation.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
                } else {
                    _validation.value =
                        ValidationModelDTO(getApplication<Application>().getString(R.string.ERROR_UNEXPECTED))
                }
            }
        })
    }
}