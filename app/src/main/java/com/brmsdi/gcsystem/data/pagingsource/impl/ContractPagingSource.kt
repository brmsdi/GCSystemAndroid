package com.brmsdi.gcsystem.data.pagingsource.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.STARTING_KEY
import com.brmsdi.gcsystem.data.dto.PaginationContractDTO
import com.brmsdi.gcsystem.data.dto.PagingContractModel
import com.brmsdi.gcsystem.data.pagingsource.PagingSourceUtils.Companion.loadResult
import com.brmsdi.gcsystem.data.service.ContractService
import retrofit2.Response

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
open class ContractPagingSource(
    private val search: String? = null,
    private val contractService: ContractService
) :
    PagingSource<Int, PagingContractModel>() {
    override fun getRefreshKey(state: PagingState<Int, PagingContractModel>): Int? {
        var current: Int? = null
        state.anchorPosition?.let {
            current = state.closestItemToPosition(it - 1)?.page
        }
        return current
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingContractModel> {
        return try {
            val start = params.key ?: STARTING_KEY
            var size = params.loadSize
            if (start > STARTING_KEY && size > 2) {
                size = 2
            }
            val requestParams: HashMap<String, String> = hashMapOf()
            requestParams[PAGE] = "$start"
            requestParams[SIZE] = "$size"
            val response: Response<PaginationContractDTO>
            if (!search.isNullOrEmpty()) {
                requestParams[Constant.PARAMS.KEY_SEARCH] = search
                response = contractService.loadContracts(requestParams)
            } else {
                response = contractService.loadContracts(requestParams)
            }

            return if (response.isSuccessful) {
                val data = response.body() as PaginationContractDTO
                val loadObject = loadResult(params, start, response)
                LoadResult.Page(data.content.map { debt ->
                    PagingContractModel(
                        debt.id,
                        data.number,
                        debt
                    )
                }, loadObject.prevKey, loadObject.nextKey)
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}