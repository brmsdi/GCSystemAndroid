package com.brmsdi.gcsystem.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ITEMS_PER_PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.STARTING_KEY
import com.brmsdi.gcsystem.data.dto.PaginationDebtDTO
import com.brmsdi.gcsystem.data.model.Debt
import com.brmsdi.gcsystem.data.service.DebtService
import kotlinx.coroutines.delay
import java.lang.Integer.max

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class DebtPagingSource(private val debtService: DebtService) :
    PagingSource<Int, Debt>() {
    override fun getRefreshKey(state: PagingState<Int, Debt>): Int? {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
        val anchorPosition = state.anchorPosition ?: return null
        val debt = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = debt.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Debt> {
        try {
            val start = params.key ?: STARTING_KEY
            val size = params.loadSize
            val requestParams = mapOf(Pair(PAGE, "$start"), Pair(SIZE, "$size"))
            val response = debtService.load(requestParams)
            delay(3_000L)
            if (response.isSuccessful) {
                val data: PaginationDebtDTO? = response.body()
                val prevKey : Int?
                val nextKey: Int?
                if (data != null) {
                    prevKey = if (data.first) null else start - 1
                    nextKey = if (data.last) {
                        null
                    } else {
                        if (start == 0) Math.floorDiv(params.loadSize, ITEMS_PER_PAGE) else start + 1
                    }
                    return LoadResult.Page(data.content, prevKey, nextKey)
                }
                return LoadResult.Page(emptyList(), null, null)
            } else {
                return LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}