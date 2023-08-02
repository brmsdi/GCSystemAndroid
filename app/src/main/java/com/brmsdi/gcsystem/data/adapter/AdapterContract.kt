package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.ContractViewHolder
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.RowContractBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterContract(
    private val locale: Locale,
    private val listener: ItemRecyclerClickListener<Contract>
) : RecyclerView.Adapter<ContractViewHolder>() {
    var list: MutableList<Contract> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowContractBinding.inflate(layoutInflater, parent, false)
        return ContractViewHolder(binding, locale, listener)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<Contract>) {
        list.clear()
        list = newList
        notifyDataSetChanged()
    }
}