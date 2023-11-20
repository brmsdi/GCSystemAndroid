package com.brmsdi.gcsystem.ui.fragment.debt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ITEMS_PER_PAGE
import com.brmsdi.gcsystem.data.dto.PagingDebtModel
import com.brmsdi.gcsystem.data.repository.DebtRepository

class DebtViewModel(application: Application, private val debtRepository: DebtRepository) :
    AndroidViewModel(application) {
    private val _searchQuery = MutableLiveData<String>()
    val loadData: LiveData<PagingData<PagingDebtModel>> = _searchQuery.switchMap { query ->
        Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { debtRepository.pagingSource(query) }
        ).liveData
    }

    fun load(search: String) {
        _searchQuery.value = search
    }
}