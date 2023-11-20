package com.brmsdi.gcsystem.ui.fragment.repairRequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.dto.PagingRepairRequestModel
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.dto.ResponseRequestDTO
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.listeners.APIEvent
import com.brmsdi.gcsystem.data.listeners.PostDeletedItemEvent
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.ui.utils.TextUtils
import retrofit2.Response
import java.net.ConnectException

class RepairRequestViewModel(
    application: Application,
    private val repairRequestRepository: RepairRequestRepository
) : AndroidViewModel(application) {
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error
    private val _searchQuery = MutableLiveData<String>()
    val loadData : LiveData<PagingData<PagingRepairRequestModel>> = _searchQuery.switchMap { query ->
        Pager(
            config = PagingConfig(Constant.PARAMS.ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { repairRequestRepository.pagingSource(query) }
        ).liveData
    }

    fun load(search: String) {
        _searchQuery.value = search
    }

    fun delete(id: Int, postDeletedItemEvent: PostDeletedItemEvent) {
        repairRequestRepository.delete(id, object : APIEvent<ResponseDTO> {
            override fun onResponse(model: ResponseDTO) {
                postDeletedItemEvent.post(model)
            }

            override fun onError(response: Response<ResponseDTO>) {
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