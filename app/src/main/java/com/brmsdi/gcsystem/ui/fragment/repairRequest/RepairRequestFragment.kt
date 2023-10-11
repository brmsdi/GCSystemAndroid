package com.brmsdi.gcsystem.ui.fragment.repairRequest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.AdapterRepairRequest
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.KEY_SEARCH
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.PAGE
import com.brmsdi.gcsystem.data.constants.Constant.PARAMS.SIZE
import com.brmsdi.gcsystem.data.constants.Constant.REPAIR.REPAIR_REQUEST_DATA_ID
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerViewDragCallback
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerListenerListener
import com.brmsdi.gcsystem.data.listeners.dialog.DialogConfirmAndCancelListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.FragmentRepairRequestBinding
import com.brmsdi.gcsystem.ui.activity.detailRepairRequest.DetailRepairRequestActivity
import com.brmsdi.gcsystem.ui.activity.newRepairRequest.NewRepairRequestActivity
import com.brmsdi.gcsystem.ui.activity.updateRepairRequest.UpdateRepairRequestActivity
import com.brmsdi.gcsystem.ui.utils.DialogAppUtils
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepairRequestFragment : Fragment(), ItemRecyclerListenerListener<RepairRequest>,
    ProgressBarOnApp {
    private val viewModel by viewModel<RepairRequestViewModel>()
    private lateinit var binding: FragmentRepairRequestBinding
    private lateinit var adapter: AdapterRepairRequest
    private var list: MutableList<RepairRequest> = mutableListOf()
    private var positionItemDeleted: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepairRequestBinding.inflate(layoutInflater, container, false)
        binding.recyclerRepair.layoutManager = LinearLayoutManager(context)
        adapter = AdapterRepairRequest(this)
        binding.recyclerRepair.adapter = adapter
        addAction()
        observe()
        val itemRecyclerViewDragCallback = ItemRecyclerViewDragCallback(this)
        val itemTouchHelper = ItemTouchHelper(itemRecyclerViewDragCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerRepair)
        return binding.root
    }

    override fun onResume() {
        loadData(null)
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val onSearchViewListener = requireActivity() as OnSearchViewListener
        onSearchViewListener.addSearchListener(addSearchEventListener())
    }

    override fun onClick(model: RepairRequest) {
        detailsRepairRequest(model)
    }

    override fun onLongClick(model: RepairRequest): Boolean {
        updateRepairRequest(model)
        return true
    }

    override fun deleteItem(position: Int) {
        if (!verifyStatusSolicitation(list[position])) {
            displayMessage(this.requireContext(), getString(R.string.repair_request_in_progress))
            adapter.updateAll(list)
            return
        }
        val dialog = DialogAppUtils
            .createDialog(this.requireContext(),
                getString(R.string.delete_this_item),
                getString(R.string.label_delete),
                getString(R.string.confirm),
                getString(R.string.cancel),
                object : DialogConfirmAndCancelListener {
                    override fun confirm() {
                        //list.removeAt(position)
                        //adapter.notifyItemRemoved(position)
                        //displayMessage(context(), getString(R.string.delete_item_success))
                        positionItemDeleted = position
                        val id = list[position].id
                        viewModel.delete(id)
                    }

                    override fun cancel() {
                        adapter.updateAll(list)
                    }
                })
        dialog.show()
    }

    private fun observe() {
        viewModel.pagination.observe(this.viewLifecycleOwner) {
            showOrHideView(binding.progressRepairRequest.root, false)
            if (it.empty) {
                binding.textSearchInfo.text = getString(R.string.search_is_empty)
                showOrHideView(binding.textSearchInfo, true)
            } else {
                showOrHideView(binding.textSearchInfo, false)
                list = it.content
                adapter.updateAll(list)
            }
        }

        viewModel.responseDelete.observe(this.viewLifecycleOwner) {
            positionItemDeleted?.let { position ->
                list.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            displayMessage(this.requireContext(), it.message)
        }

        viewModel.error.observe(this.viewLifecycleOwner) {
            if (!it.status()) {
                showOrHideView(binding.progressRepairRequest.root, false)
                binding.textSearchInfo.text = it.message()
                showOrHideView(binding.textSearchInfo, true)
                displayMessage(this.requireContext(), it.message())
            }
        }
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
        bundle.putInt(REPAIR_REQUEST_DATA_ID, repairRequest.id)
        val intent = Intent(this.requireContext(), DetailRepairRequestActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun updateRepairRequest(repairRequest: RepairRequest) {
        if (!verifyStatusSolicitation(repairRequest)) {
            displayMessage(this.requireContext(), getString(R.string.repair_request_in_progress))
            return
        }
        val bundle = Bundle()
        bundle.putInt(REPAIR_REQUEST_DATA_ID, repairRequest.id)
        val intent = Intent(this.requireContext(), UpdateRepairRequestActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun loadData(search: String?, page: UInt = 0u, size: UInt = 10u) {
        showOrHideView(binding.textSearchInfo, false)
        showOrHideView(binding.progressRepairRequest.root, true)
        list.clear()
        search?.let { text ->
            viewModel.search(
                mapOf(
                    Pair(PAGE, page.toString()),
                    Pair(SIZE, size.toString()),
                    Pair(KEY_SEARCH, text)
                )
            )
            return
        }
        viewModel.loadRepairRequests(
            mapOf(
                Pair(PAGE, page.toString()),
                Pair(SIZE, size.toString())
            )
        )
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

    private fun verifyStatusSolicitation(repairRequest: RepairRequest): Boolean {
        return (repairRequest.status.name.uppercase() != getString(R.string.status_in_progress).uppercase()
                && repairRequest.status.name.uppercase() != getString(R.string.status_concluded).uppercase())
    }
}