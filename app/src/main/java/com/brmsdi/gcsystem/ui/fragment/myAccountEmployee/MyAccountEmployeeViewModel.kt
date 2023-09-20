package com.brmsdi.gcsystem.ui.fragment.myAccountEmployee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.model.Employee
import com.brmsdi.gcsystem.data.repository.EmployeeRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class MyAccountEmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val _account = MutableLiveData<Employee>()
    val account: LiveData<Employee> = _account
    private val _validation = MutableLiveData<ValidationModelDTO>()
    val validation: LiveData<ValidationModelDTO> = _validation

    fun getDataAccount(employeeRepository: EmployeeRepository) {
        employeeRepository.myAccount(object : APIEvent<Employee> {
            override fun onResponse(model: Employee) {
                _account.value = model
            }

            override fun onError(response: Response<Employee>) {
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