package com.brmsdi.gcsystem.data.adapter.viewHolder

import android.content.Context
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.RemoveItemListener
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.RowItemBinding
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.formatCoin
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ItemViewHolder(
    private val context: Context,
    private val locale: Locale,
    private val row: RowItemBinding,
    private val removeItemListener: RemoveItemListener
) :
    RecyclerView.ViewHolder(row.root) {

    fun bindData(item: Item, orderService: OrderService) {
        row.textId.text = item.id.toString()
        row.textDescription.text = item.description
        row.textQuantity.text = item.quantity.toString()
        row.textMoney.text = formatCoin(locale, item.value)
        if (orderService.status.name != context.getString(R.string.status_concluded)) {
            row.buttonDelete.visibility = VISIBLE
            row.buttonDelete.setOnClickListener { removeItemListener.remove(item) }
        }
    }
}