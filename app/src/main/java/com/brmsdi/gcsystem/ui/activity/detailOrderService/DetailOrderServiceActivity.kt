package com.brmsdi.gcsystem.ui.activity.detailOrderService

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.AdapterOrderServiceRepairRequests
import com.brmsdi.gcsystem.data.constants.Constant.OS.ORDER_SERVICE_DATA
import com.brmsdi.gcsystem.data.dto.EmployeeSpinnerDTO
import com.brmsdi.gcsystem.data.dto.SpinnerDTO
import com.brmsdi.gcsystem.data.listeners.ItemDialogListener
import com.brmsdi.gcsystem.data.listeners.AddItemListener
import com.brmsdi.gcsystem.data.listeners.RemoveItemListener
import com.brmsdi.gcsystem.data.listeners.dialog.DialogConfirmAndCancelListener
import com.brmsdi.gcsystem.data.model.Employee
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.ActivityDetailOrderServiceBinding
import com.brmsdi.gcsystem.ui.utils.DateUtils.Companion.dateFormattedToView
import com.brmsdi.gcsystem.ui.utils.DialogAppUtils.Companion.addItemDialog
import com.brmsdi.gcsystem.ui.utils.DialogAppUtils.Companion.closeDialog
import com.brmsdi.gcsystem.ui.utils.DialogAppUtils.Companion.createDialog
import com.brmsdi.gcsystem.ui.utils.LoadData
import com.brmsdi.gcsystem.ui.utils.Mock.Companion.statusList
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import java.lang.NumberFormatException

class DetailOrderServiceActivity : AppCompatActivity(), LoadData, AddItemListener,
    OnClickListener, RemoveItemListener {
    private lateinit var binding: ActivityDetailOrderServiceBinding
    private var orderService: OrderService? = null
    private lateinit var spinnerAdapterCollaborators: ArrayAdapter<SpinnerDTO<Employee>>
    private lateinit var recyclerOrderServiceRepairRequests: RecyclerView
    private lateinit var addItemDialog: AlertDialog
    private lateinit var removeItemDialog: AlertDialog
    private lateinit var finalizeOrderServiceDialog: AlertDialog
    private lateinit var adapterRepair: AdapterOrderServiceRepairRequests

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderServiceBinding.inflate(layoutInflater)
        orderService = load(intent.getBundleExtra(ORDER_SERVICE_DATA), ORDER_SERVICE_DATA, OrderService::class.java)
        loadData(orderService)
        addAction()
        setContentView(binding.root)
    }

    private fun loadData(orderService: OrderService?) {
        orderService?.let {
            binding.editId.setText(orderService.id.toString())
            binding.editCreatedDate.setText(dateFormattedToView(orderService.generationDate))
            orderService.reservedDate?.let { reservedDate ->
                binding.editReservedDate.setText(dateFormattedToView(reservedDate))
            }
            binding.editStatus.setText(orderService.status.name)
            if (orderService.employees.isNotEmpty()) {
                val employees =
                    orderService.employees.map { employee -> EmployeeSpinnerDTO(employee) }
                        .toMutableList()
                spinnerAdapterCollaborators = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    mutableListOf<SpinnerDTO<Employee>>()
                )
                binding.spinnerCollaborators.adapter = spinnerAdapterCollaborators
                spinnerAdapterCollaborators.addAll(employees)
                binding.spinnerCollaborators.visibility = VISIBLE
                binding.textCollaboratorsNone.visibility = GONE
            }
            val repair = orderService.repairRequests
            adapterRepair =
                AdapterOrderServiceRepairRequests(
                    context = this,
                    orderService = it,
                    addItemListener = this,
                    removeItemListener = this
                )
            recyclerOrderServiceRepairRequests = binding.recyclerOrderServiceRepairRequests
            recyclerOrderServiceRepairRequests.layoutManager =
                LinearLayoutManager(this)
            // addSnapHelper(recyclerOrderServiceRepairRequests, LinearSnapHelper())
            adapterRepair.updateAll(repair.toMutableList())
            recyclerOrderServiceRepairRequests.adapter = adapterRepair

            if (isCompleted(orderService)) {
                binding.buttonFinalizeOrderService.visibility = GONE
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonFinalizeOrderService.id -> {
                finalizeOrderServiceDialog = createDialog(
                    getContext(),
                    getString(R.string.finalize_order_service),
                    getString(R.string.finalize),
                    getString(R.string.confirm),
                    getString(R.string.cancel),
                    object : DialogConfirmAndCancelListener {
                        override fun confirm() {
                            orderService?.let {
                                finalizeOrderService(it)
                                displayMessage(getContext(), getString(R.string.finished_os_success))
                                finish()
                            }
                        }

                        override fun cancel() {
                            closeDialog(finalizeOrderServiceDialog)
                        }
                    }
                )
                finalizeOrderServiceDialog.show()
            }

            binding.buttonBack.id -> finish()
        }
    }

    private fun addAction() {
        binding.buttonFinalizeOrderService.setOnClickListener(this)
        binding.buttonBack.setOnClickListener(this)
    }

    override fun addItem(repairRequest: RepairRequest) {
        addItemDialog = addItemDialog(
            this,
            object : ItemDialogListener {
                override fun add(description: String, quantity: String, value: String) {
                    val quantityParsed: Int
                    val valueParsed: Double
                    if (!fieldsIsNotEmpty(description, quantity, value)) {
                        displayMessage(getContext(), getString(R.string.fields_empty))
                        return
                    }

                    try {
                        quantityParsed = quantity.toInt()
                        valueParsed = value.toDouble()
                    } catch (_: NumberFormatException) {
                        displayMessage(getContext(), getString(R.string.fields_empty))
                        return
                    }

                    val repairRequestMock: RepairRequest =
                        adapterRepair.getList().single { it.id == repairRequest.id }
                    //MOCK
                    val itemID =
                        if (repairRequestMock.items != null && repairRequestMock.items!!.isNotEmpty()) repairRequestMock.items!!.count() + 1 else 1
                    val item = Item(itemID, description, quantityParsed, valueParsed)
                    if (adapterRepair.addItem(repairRequest, item)) {
                        displayMessage(getContext(), getString(R.string.item_add_success))
                        orderService!!.repairRequests = adapterRepair.getList()
                    }
                    closeDialog(addItemDialog)
                }

                override fun cancel() {
                    closeDialog(addItemDialog)
                }
            })
        addItemDialog.show()
    }

    override fun remove(item: Item) {
        removeItemDialog = createDialog(
            this,
            getString(R.string.delete_this_item),
            getString(R.string.label_delete),
            getString(R.string.confirm),
            getString(R.string.cancel),
            object : DialogConfirmAndCancelListener {
                override fun confirm() {
                    val repair = adapterRepair.getList().singleOrNull {
                        !it.items.isNullOrEmpty() && it.items!!.contains(item)
                    }
                    if (repair != null) {
                        if (adapterRepair.removeItem(repair, item)) {
                            displayMessage(
                                getContext(),
                                getString(R.string.delete_item_success)
                            )
                            orderService!!.repairRequests = adapterRepair.getList()
                        }
                    }
                    closeDialog(removeItemDialog)
                }

                override fun cancel() {
                    closeDialog(removeItemDialog)
                }
            }
        )

        removeItemDialog.show()
    }

    private fun getContext() = this

    private fun isCompleted(orderService: OrderService): Boolean {
        return orderService.status.name == getString(R.string.status_concluded)
    }

    fun finalizeOrderService(orderService: OrderService) {
        orderService.status = statusList()[3]
    }
}