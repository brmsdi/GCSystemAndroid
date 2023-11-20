package com.brmsdi.gcsystem.data.pagingsource

import androidx.paging.PagingSource
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ITEMS_PER_PAGE
import com.brmsdi.gcsystem.data.dto.LoadResultParamsDTO
import com.brmsdi.gcsystem.data.dto.PaginationDTO
import retrofit2.Response

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PagingSourceUtils {

    companion object {
        fun loadResult(
            params: PagingSource.LoadParams<Int>,
            start: Int,
            response: Response<out PaginationDTO>
        ): LoadResultParamsDTO {
            val data = response.body() as PaginationDTO
            return LoadResultParamsDTO(
                prevKey = if (start == 0) null else start - 1,
                nextKey = if (data.last) {
                    null
                } else {
                    if (start == 0) Math.floorDiv(
                        params.loadSize,
                        ITEMS_PER_PAGE
                    ) else start + 1
                }
            )
        }
    }
}