package com.brmsdi.gcsystem.ui.fragment.contract

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
import com.brmsdi.gcsystem.data.dto.PagingContractModel
import com.brmsdi.gcsystem.data.repository.ContractRepository

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ContractViewModel(
    application: Application,
    private val contractRepository: ContractRepository
) : AndroidViewModel(application) {
    private val _searchQuery = MutableLiveData<String>()
    val loadData: LiveData<PagingData<PagingContractModel>> = _searchQuery.switchMap { query ->
        Pager(
            config = PagingConfig(Constant.PARAMS.ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { contractRepository.pagingSource(query) }
        ).liveData
    }

    fun load(search: String) {
        _searchQuery.value = search
    }
}