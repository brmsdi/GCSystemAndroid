package com.brmsdi.gcsystem.data.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.RowOrderServiceBinding
import com.brmsdi.gcsystem.ui.utils.DateUtils.Companion.dateFormattedToView

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class OrderServiceViewHolder(
    private val rowOrderServiceBinding: RowOrderServiceBinding,
    private val listener: ItemRecyclerClickListener<OrderService>
) : ViewHolder(rowOrderServiceBinding.root) {

    fun bindData(orderService: OrderService) {
        rowOrderServiceBinding.textId.text = orderService.id.toString()
        rowOrderServiceBinding.textCreatedDate.text = dateFormattedToView(orderService.generationDate)
        orderService.reservedDate?.let {
            rowOrderServiceBinding.textReservedDate.text = dateFormattedToView(it)
        }
        orderService.completionDate?.let {
            rowOrderServiceBinding.textConclusionDate.text = dateFormattedToView(it)
        }
        rowOrderServiceBinding.textStatus.text = orderService.status.name
        rowOrderServiceBinding.root.setOnClickListener {
            listener.onClick(orderService)
        }
    }
}