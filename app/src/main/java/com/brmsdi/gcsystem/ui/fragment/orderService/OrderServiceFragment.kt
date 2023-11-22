package com.brmsdi.gcsystem.ui.fragment.orderService

import android.content.Context
import android.content.Intent
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
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.ID_ORDER_SERVICE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.FragmentOrderServiceBinding
import com.brmsdi.gcsystem.ui.activity.detailOrderService.DetailOrderServiceActivity
import com.brmsdi.gcsystem.ui.utils.ColorUtils.Companion.getColorSwipeRefreshLayout
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderServiceFragment : Fragment(), ItemRecyclerClickListener<OrderService>, ProgressBarOnApp {
    private lateinit var binding: FragmentOrderServiceBinding
    private val viewModel by viewModel<OrderServiceViewModel>()
    private lateinit var adapter: AdapterOrderService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderServiceBinding.inflate(inflater, container, false)
        adapter = AdapterOrderService(this)
        binding.recyclerOrderService.layoutManager = LinearLayoutManager(context)
        binding.recyclerOrderService.adapter = adapter
        observe()
        addAction()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val onSearchViewListener = requireActivity() as OnSearchViewListener
        onSearchViewListener.addSearchListener(addSearchEventListener())
    }

    override fun onResume() {
        super.onResume()
        loadData(null)
    }

    override fun onClick(model: OrderService) {
        detailsOrderService(model)
    }

    private fun observe() {
        viewModel.pagination.observe(this.viewLifecycleOwner) {
            if (it.empty) {
                binding.textInfo.text = getString(R.string.search_is_empty)
                showOrHideView(binding.textInfo, true)
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                showOrHideView(binding.textInfo, false)
                adapter.updateAll(it.content)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.error.observe(this.viewLifecycleOwner) {
            if (!it.status()) {
                adapter.updateAll(mutableListOf())
                binding.textInfo.text = it.message()
                showOrHideView(binding.textInfo, true)
                binding.swipeRefreshLayout.isRefreshing = false
                displayMessage(this.requireContext(), it.message())

            }
        }
    }

    private fun addAction() {
        getColorSwipeRefreshLayout(resources, binding.swipeRefreshLayout)
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData(null)
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
        if (text.isEmpty()) {
            loadData(null)
            return
        }
        loadData(text)
    }

    private fun detailsOrderService(orderService: OrderService) {
        val bundle = Bundle()
        bundle.putInt(Constant.OS.ID_ORDER_SERVICE, orderService.id)
        val intent = Intent(this.requireContext(), DetailOrderServiceActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun loadData(search: String?, page: UInt = 0u, size: UInt = 10u) {
        binding.swipeRefreshLayout.isRefreshing = true
        showOrHideView(binding.textInfo, false)
        adapter.updateAll(mutableListOf())
        search?.let { text ->
            viewModel.search(
                mapOf(
                    Pair(PAGE, page.toString()),
                    Pair(SIZE, size.toString()),
                    Pair(ID_ORDER_SERVICE, text)
                )
            )
            return
        }
        viewModel.loadOrderServices(
            mapOf(
                Pair(PAGE, page.toString()),
                Pair(SIZE, size.toString())
            )
        )
    }
}