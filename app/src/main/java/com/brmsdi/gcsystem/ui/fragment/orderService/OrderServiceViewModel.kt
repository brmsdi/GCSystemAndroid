package com.brmsdi.gcsystem.ui.fragment.orderService

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.dto.PagingOrderServiceModel
import com.brmsdi.gcsystem.data.dto.ValidationModelDTO
import com.brmsdi.gcsystem.data.repository.OrderServiceRepository

class OrderServiceViewModel(
    application: Application,
    private val orderServiceRepository: OrderServiceRepository
) : AndroidViewModel(application) {
    private val _error = MutableLiveData<ValidationModelDTO>()
    val error: LiveData<ValidationModelDTO> = _error
    private val _searchQuery = MutableLiveData<String>()
    val loadData: LiveData<PagingData<PagingOrderServiceModel>> = _searchQuery.switchMap { query ->
        Pager(
            config = PagingConfig(
                pageSize = Constant.PARAMS.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { orderServiceRepository.pagingSource(query) }
        ).liveData
    }

    fun load(search: String) {
        _searchQuery.value = search
    }
}