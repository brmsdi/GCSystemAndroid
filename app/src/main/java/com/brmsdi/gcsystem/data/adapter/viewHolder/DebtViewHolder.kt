package com.brmsdi.gcsystem.data.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.model.Debt
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.databinding.RowDebtBinding
import com.brmsdi.gcsystem.ui.utils.DateUtils.Companion.dateFormattedToView
import com.brmsdi.gcsystem.ui.utils.NumberUtils
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class DebtViewHolder(
    private val row: RowDebtBinding,
    private val locale: Locale
) :
    RecyclerView.ViewHolder(row.root) {
    fun bindData(debt: Debt) {
        row.textId.text = debt.id.toString()
        row.textValue.text = NumberUtils.formatCoin(locale, debt.value)
        row.textDueDate.text = dateFormattedToView(debt.dueDate)
        row.textDebtor.text = debt.lessee.name
        row.textStatus.text = debt.status.name
    }
}