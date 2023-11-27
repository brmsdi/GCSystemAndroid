package com.brmsdi.gcsystem.data.pagingsource.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ID_ORDER_SERVICE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.STARTING_KEY
import com.brmsdi.gcsystem.data.dto.PaginationOrderServiceDTO
import com.brmsdi.gcsystem.data.dto.PagingOrderServiceModel
import com.brmsdi.gcsystem.data.pagingsource.PagingSourceUtils
import com.brmsdi.gcsystem.data.service.OrderServiceService
import retrofit2.Response

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class OrderServiceSource (
    private val search: String? = null,
    private val orderServiceService: OrderServiceService
) : PagingSource<Int, PagingOrderServiceModel>() {

    override fun getRefreshKey(state: PagingState<Int, PagingOrderServiceModel>): Int? {
        var current : Int? = null
        state.anchorPosition?.let {
            current = state.closestItemToPosition(it-1)?.page
        }
        return current
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingOrderServiceModel> {
        return try {
            val start = params.key ?: STARTING_KEY
            var size = params.loadSize
            if (start > STARTING_KEY && size > 2) {
                size = 2
            }
            val requestParams: HashMap<String, String> = hashMapOf()
            requestParams[PAGE] = "$start"
            requestParams[SIZE] = "$size"
            val response : Response<PaginationOrderServiceDTO>
            if (!search.isNullOrEmpty()) {
                requestParams[ID_ORDER_SERVICE] = search
                response = orderServiceService.search(requestParams)
            } else {
                response = orderServiceService.loadOrderService(requestParams)
            }
            return if (response.isSuccessful) {
                val data = response.body() as PaginationOrderServiceDTO
                val loadObject = PagingSourceUtils.loadResult(params, start, response)
                LoadResult.Page(data.content.map { os -> PagingOrderServiceModel(os.id, data.number, os) }, loadObject.prevKey, loadObject.nextKey)
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}