package com.brmsdi.gcsystem.ui.fragment.orderService

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.AdapterOrderService
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.constants.Constant.OS.ORDER_SERVICE_DATA
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerListener
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.FragmentOrderServiceBinding
import com.brmsdi.gcsystem.ui.activity.detailOrderService.DetailOrderServiceActivity
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp

class OrderServiceFragment : Fragment(), ItemRecyclerListener<OrderService>, ProgressBarOnApp {
    private lateinit var binding: FragmentOrderServiceBinding
    private lateinit var viewModel: OrderServiceViewModel
    private var list: MutableList<OrderService> = mutableListOf()
    private lateinit var adapter: AdapterOrderService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderServiceBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[OrderServiceViewModel::class.java]
        adapter = AdapterOrderService(this)
        binding.recyclerOrderService.layoutManager = LinearLayoutManager(context)
        binding.recyclerOrderService.adapter = adapter
        observe()
        viewModel.getAll(null)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val onSearchViewListener = requireActivity() as OnSearchViewListener
        onSearchViewListener.addSearchListener(addSearchEventListener())
    }

    override fun onClick(model: OrderService) {
        detailsOrderService(model)
    }

    override fun onLongClick(model: OrderService): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteItem(position: Int) {
        TODO("Not yet implemented")
    }

    private fun observe() {
        viewModel.list.observe(this.viewLifecycleOwner) {
            list = it
            if (list.isEmpty()) {
                binding.textSearchInfo.text = getString(R.string.search_is_empty)
                showOrHideView(binding.textSearchInfo, true)
            } else {
                showOrHideView(binding.textSearchInfo, false)
            }
            adapter.updateAll(list)
        }
    }

    private fun addSearchEventListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearch(newText)
                return true
            }
        }
    }

    private fun performSearch(text: String) {
        if (text.isEmpty()) viewModel.getAll(null) else viewModel.getAll(text)
    }

    private fun detailsOrderService(orderService: OrderService) {
        val bundle = Bundle()
        bundle.putParcelable(ORDER_SERVICE_DATA, orderService)
        bundle.putParcelable("item", Item(1, "edd", 2, 20.0))
        val intent = Intent(this.requireContext(), DetailOrderServiceActivity::class.java)
        intent.putExtra(ORDER_SERVICE_DATA, bundle)
        startActivity(intent)
    }
}