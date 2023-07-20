package com.brmsdi.gcsystem.data.listeners

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.model.RepairRequest

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ItemRecyclerViewDragCallback(private val listener: ItemRecyclerListener<RepairRequest>) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.deleteItem(viewHolder.adapterPosition)
    }
}