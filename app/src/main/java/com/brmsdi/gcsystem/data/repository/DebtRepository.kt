package com.brmsdi.gcsystem.data.repository

import com.brmsdi.gcsystem.data.model.Debt
import com.brmsdi.gcsystem.data.pagingsource.GenericPagingSource


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
interface DebtRepository : CallRepository {

    fun pagingSource() : GenericPagingSource<Debt>
}