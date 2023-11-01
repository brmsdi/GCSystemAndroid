package com.brmsdi.gcsystem.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ITEMS_PER_PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.STARTING_KEY
import com.brmsdi.gcsystem.data.dto.PaginationDTO
import com.brmsdi.gcsystem.data.service.DebtService
import java.lang.Integer.max

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
@Suppress("UNCHECKED_CAST")
class GenericPagingSource<E : PagingModel<E>>(private val debtService: DebtService) :
    PagingSource<Int, E>() {
    override fun getRefreshKey(state: PagingState<Int, E>): Int? {
        // In our case we grab the item closest to the anchor position
        // then return its id - (state.config.pageSize / 2) as a buffer
        val anchorPosition = state.anchorPosition ?: return null
        val model = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = model.getPagingID() - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, E> {
        try {
            val start = params.key ?: STARTING_KEY
            val size = params.loadSize
            val requestParams = mapOf(Pair(PAGE, "$start"), Pair(SIZE, "$size"))
            val response = debtService.load(requestParams)
            return if (response.isSuccessful) {
                val data = response.body() as PaginationDTO<E>
                val prevKey : Int? = if (data.first) null else start - 1
                val nextKey: Int? = if (data.last) {
                    null
                } else {
                    if (start == 0) Math.floorDiv(params.loadSize, ITEMS_PER_PAGE) else start + 1
                }
                LoadResult.Page(data.content.map { it.getModel() }.toList(), prevKey, nextKey)
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}