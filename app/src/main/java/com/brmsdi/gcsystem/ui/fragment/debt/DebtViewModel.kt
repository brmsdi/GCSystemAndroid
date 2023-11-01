package com.brmsdi.gcsystem.ui.fragment.debt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ITEMS_PER_PAGE
import com.brmsdi.gcsystem.data.model.Debt
import com.brmsdi.gcsystem.data.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
class DebtViewModel(application: Application, private val debtRepository: DebtRepository) : AndroidViewModel(application) {
    val items: Flow<PagingData<Debt>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { debtRepository.pagingSource() }
    ).flow.cachedIn(viewModelScope)
}