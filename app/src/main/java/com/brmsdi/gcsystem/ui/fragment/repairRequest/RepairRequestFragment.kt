package com.brmsdi.gcsystem.ui.fragment.repairRequest

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
import com.brmsdi.gcsystem.data.adapter.AdapterRepairRequest
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.data.listeners.RepairRequestListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.FragmentRepairRequestBinding
import com.brmsdi.gcsystem.ui.activity.detailRepairRequest.DetailRepairRequestActivity
import com.brmsdi.gcsystem.ui.activity.newRepairRequest.NewRepairRequestActivity
import com.brmsdi.gcsystem.ui.utils.Mock
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp

class RepairRequestFragment : Fragment(), RepairRequestListener, ProgressBarOnApp {
    private lateinit var viewModel: RepairRequestViewModel
    private lateinit var binding: FragmentRepairRequestBinding
    private val adapter = AdapterRepairRequest()
    private var list: MutableList<RepairRequest> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepairRequestBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[RepairRequestViewModel::class.java]
        binding.recyclerRepair.layoutManager = LinearLayoutManager(context)
        binding.recyclerRepair.adapter = adapter
        adapter.addListener(this)
        addAction()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData(null)
        adapter.updateRepairRequestAll(list)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val onSearchViewListener = requireActivity() as OnSearchViewListener
        onSearchViewListener.addSearchListener(addSearchEventListener())
    }

    override fun onClick(repairRequest: RepairRequest) {
        detailsRepairRequest(repairRequest)
    }

    private fun addAction() {
        binding.floatingNewRepair.setOnClickListener {
            newRepairRequest()
        }
    }

    private fun newRepairRequest() {
        startActivity(Intent(this.requireContext(), NewRepairRequestActivity::class.java))
    }

    private fun detailsRepairRequest(repairRequest: RepairRequest) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.REPAIR.REPAIR_REQUEST_DATA, repairRequest)
        val intent = Intent(this.requireContext(), DetailRepairRequestActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun loadData(search: String?) {
        list.clear()
        search?.let { text ->
            Mock.listRepairRequestList().forEach {
                if (text == it.id.toString() || contains(text, it.condominium.name)) list.add(it)
            }
            return
        }
        list = Mock.listRepairRequestList()
    }

    private fun addSearchEventListener() :SearchView.OnQueryTextListener {
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
            showOrHideView(binding.textSearchInfo, false)
            adapter.updateRepairRequestAll(list)
            return
        }
        loadData(text)
        if (list.isEmpty()) {
            binding.textSearchInfo.text = getString(R.string.search_is_empty)
            showOrHideView(binding.textSearchInfo, true)
        } else {
            showOrHideView(binding.textSearchInfo, false)
        }
        adapter.updateRepairRequestAll(list)
    }

    private fun contains(key: String, text: String): Boolean {
        return text.uppercase().contains(key.uppercase())
    }
}