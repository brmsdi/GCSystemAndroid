package com.brmsdi.gcsystem.data.pagingsource.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.KEY_SEARCH
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.STARTING_KEY
import com.brmsdi.gcsystem.data.dto.PaginationRepairRequestDTO
import com.brmsdi.gcsystem.data.dto.PagingRepairRequestModel
import com.brmsdi.gcsystem.data.pagingsource.PagingSourceUtils
import com.brmsdi.gcsystem.data.service.RepairRequestService
import retrofit2.Response

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class RepairRequestPagingSource (
    private val search: String? = null,
    private val repairRequestService: RepairRequestService
) : PagingSource<Int, PagingRepairRequestModel>() {

    override fun getRefreshKey(state: PagingState<Int, PagingRepairRequestModel>): Int? {
        var current : Int? = null
        state.anchorPosition?.let {
            current = state.closestItemToPosition(it-1)?.page
        }
        return current
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingRepairRequestModel> {
        return try {
            val start = params.key ?: STARTING_KEY
            var size = params.loadSize
            if (start > STARTING_KEY && size > 2) {
                size = 2
            }
            val requestParams: HashMap<String, String> = hashMapOf()
            requestParams[PAGE] = "$start"
            requestParams[SIZE] = "$size"
            val response : Response<PaginationRepairRequestDTO>
            if (!search.isNullOrEmpty()) {
                requestParams[KEY_SEARCH] = search
                response = repairRequestService.search(requestParams)
            } else {
                response = repairRequestService.load(requestParams)
            }
            return if (response.isSuccessful) {
                val data = response.body() as PaginationRepairRequestDTO
                val loadObject = PagingSourceUtils.loadResult(params, start, response)
                LoadResult.Page(data.content.map { rp -> PagingRepairRequestModel(rp.id, data.number, rp) }, loadObject.prevKey, loadObject.nextKey)
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}