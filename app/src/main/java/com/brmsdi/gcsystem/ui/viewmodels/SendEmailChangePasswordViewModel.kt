package com.brmsdi.gcsystem.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.APIEventStringAndJSON
import com.brmsdi.gcsystem.data.repositories.EmployeeRepository
import com.brmsdi.gcsystem.data.repositories.LesseeRepository
import com.brmsdi.gcsystem.data.repositories.impl.EmployeeRepositoryImpl
import com.brmsdi.gcsystem.data.repositories.impl.LesseeRepositoryImpl
import com.brmsdi.gcsystem.ui.utils.ResponseRequest
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.jsonToObject
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class SendEmailChangePasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val _responseRequest = MutableLiveData<ResponseRequest>()
    val responseRequest : LiveData<ResponseRequest> = _responseRequest
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage
    private val employeeRepository : EmployeeRepository = EmployeeRepositoryImpl()
    private val lesseeRepository : LesseeRepository = LesseeRepositoryImpl()

    private var executor: Executor = Executors.newSingleThreadExecutor()

    fun requestCode(email: String, typeAuth: String) {
        if (typeAuth == this.getApplication<Application>().getString(R.string.employee))
        {
            executor.execute {
                requestCodeEmployee(email)
            }
        } else if (typeAuth == this.getApplication<Application>().getString(R.string.lessee)) {
            executor.execute {
                requestCodeLessee(email)
            }
        }
    }

    private fun requestCodeEmployee(email : String) {
        employeeRepository.requestCode(email, object : APIEventStringAndJSON {
            override fun onSuccess() {
                val responseRequest = ResponseRequest()
                responseRequest.status = HttpsURLConnection.HTTP_OK
                _responseRequest.postValue(responseRequest)
            }

            override fun onError(errorString: String) {
                _responseRequest.postValue(jsonToObject(errorString, ResponseRequest::class.java))
            }

            override fun onConnectFailure() {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
            }
        })
    }

    private fun requestCodeLessee(email : String) {
        lesseeRepository.requestCode(email, object : APIEventStringAndJSON {
            override fun onSuccess() {
                val responseRequest = ResponseRequest()
                responseRequest.status = HttpsURLConnection.HTTP_OK
                _responseRequest.postValue(responseRequest)
            }

            override fun onError(errorString: String) {
                _responseRequest.postValue(jsonToObject(errorString, ResponseRequest::class.java))
            }

            override fun onConnectFailure() {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.ERROR_CONNECTION))
            }
        })
    }
}