package com.brmsdi.gcsystem.ui.fragment.repairRequest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.PagingDataRepairRequest
import com.brmsdi.gcsystem.data.constants.Constant.REPAIR.REPAIR_REQUEST_DATA_ID
import com.brmsdi.gcsystem.data.dto.ResponseDTO
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerViewDragCallback
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerListenerListener
import com.brmsdi.gcsystem.data.listeners.PostDeletedItemEvent
import com.brmsdi.gcsystem.data.listeners.dialog.DialogConfirmAndCancelListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.FragmentRepairRequestBinding
import com.brmsdi.gcsystem.ui.activity.detailRepairRequest.DetailRepairRequestActivity
import com.brmsdi.gcsystem.ui.activity.newRepairRequest.NewRepairRequestActivity
import com.brmsdi.gcsystem.ui.activity.updateRepairRequest.UpdateRepairRequestActivity
import com.brmsdi.gcsystem.ui.utils.ColorUtils
import com.brmsdi.gcsystem.ui.utils.DialogAppUtils
import com.brmsdi.gcsystem.ui.utils.DialogAppUtils.Companion.closeDialog
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepairRequestFragment : Fragment(), ItemRecyclerListenerListener<RepairRequest>,
    ProgressBarOnApp {
    private val viewModel by viewModel<RepairRequestViewModel>()
    private lateinit var binding: FragmentRepairRequestBinding
    private lateinit var adapter: PagingDataRepairRequest
    private var search = ""
    private lateinit var itemRecyclerViewDragCallback: ItemRecyclerViewDragCallback
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var dialog: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepairRequestBinding.inflate(layoutInflater, container, false)
        binding.recyclerRepair.layoutManager = LinearLayoutManager(context)
        adapter = PagingDataRepairRequest(this)
        binding.recyclerRepair.adapter = adapter
        binding.progressRepairRequest.root.visibility = View.VISIBLE
        observe()
        addAction()
        ColorUtils.getColorSwipeRefreshLayout(resources, binding.swipeRefreshLayout)
        viewModel.load(search)
        itemRecyclerViewDragCallback = ItemRecyclerViewDragCallback(this)
        itemTouchHelper = ItemTouchHelper(itemRecyclerViewDragCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerRepair)
        return binding.root
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
        val itemRemove = adapter.peek(position)?.repairRequest ?: return
        if (!verifyStatusSolicitation(itemRemove)) {
            displayMessage(this.requireContext(), getString(R.string.repair_request_in_progress))
            return
        }
        dialog = DialogAppUtils
            .createDialog(this.requireContext(),
                getString(R.string.delete_this_item),
                getString(R.string.label_delete),
                getString(R.string.confirm),
                getString(R.string.cancel),
                object : DialogConfirmAndCancelListener {
                    override fun confirm() {
                        deleteItem(itemRemove)
                        closeDialog(dialog)
                    }

                    override fun cancel() {
                        binding.recyclerRepair.findViewHolderForAdapterPosition(position)?.let {
                            itemRecyclerViewDragCallback.clearView(binding.recyclerRepair, it)
                            closeDialog(dialog)
                        }
                    }
                })
        dialog.show()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        viewModel.loadData.observe(this.viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.error.observe(this.viewLifecycleOwner) {
            if (!it.status()) {
                showOrHideView(binding.progressRepairRequest.root, false)
                binding.textInfo.text = it.message()
                showOrHideView(binding.textInfo, true)
                displayMessage(this.requireContext(), it.message())
            }
        }
    }

    private fun addAction() {
        binding.floatingNewRepair.setOnClickListener {
            newRepairRequest()
        }
        adapter.addOnPagesUpdatedListener {
            verifyData()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.load(search)
        }
    }

    private fun that() = this

    private fun verifyData() {
        removeLoadingComponent()
        if (adapter.itemCount <= 0) {
            binding.textInfo.text = getString(R.string.search_is_empty)
            binding.textInfo.isVisible = true
            return
        }
        binding.textInfo.isVisible = false
    }

    private fun removeLoadingComponent() {
        showOrHideView(binding.progressRepairRequest.root, false)
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun newRepairRequest() {
        startForResult.launch(Intent(this.requireContext(), NewRepairRequestActivity::class.java))
    }

    private fun detailsRepairRequest(repairRequest: RepairRequest) {
        val bundle = Bundle()
        bundle.putInt(REPAIR_REQUEST_DATA_ID, repairRequest.id)
        val intent = Intent(this.requireContext(), DetailRepairRequestActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK || result.resultCode == Activity.RESULT_CANCELED) {
                adapter.refresh()
            }
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
        startForResult.launch(intent)
    }

    private fun deleteItem(repairRequest: RepairRequest) {
        viewModel.delete(repairRequest.id, object : PostDeletedItemEvent {
            override fun post(responseDTO: ResponseDTO) {
                displayMessage(that().requireContext(), responseDTO.message)
                adapter.refresh()
            }
        })
    }

    private fun addSearchEventListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search = query
                if (search.isNotEmpty()) viewModel.load(search)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                search = newText
                if (search.isEmpty()) viewModel.load(search)
                return true
            }
        }
    }

    private fun verifyStatusSolicitation(repairRequest: RepairRequest): Boolean {
        return (repairRequest.status.name.uppercase() != getString(R.string.status_in_progress).uppercase()
                && repairRequest.status.name.uppercase() != getString(R.string.status_concluded).uppercase())
    }
}