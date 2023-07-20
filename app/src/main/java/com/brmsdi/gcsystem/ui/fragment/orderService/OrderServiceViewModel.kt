package com.brmsdi.gcsystem.ui.fragment.orderService

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.ui.utils.Mock

class OrderServiceViewModel : ViewModel() {
    private val _list = MutableLiveData<MutableList<OrderService>>()
    val list: LiveData<MutableList<OrderService>> = _list

    fun getAll(search: String?) {
        var temp = Mock.listOrderService()
        search?.let { text ->
            temp = temp.filter { orderService -> orderService.id.toString() == text }.toMutableList()
        }
        _list.value = temp
    }
}