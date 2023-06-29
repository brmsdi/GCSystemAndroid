package com.brmsdi.gcsystem.ui.fragments.myAccountEmployee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.ui.viewmodels.MyAccountEmployeeViewModel

class MyAccountEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = MyAccountEmployeeFragment()
    }

    private lateinit var viewModel: MyAccountEmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_account_employee, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyAccountEmployeeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}