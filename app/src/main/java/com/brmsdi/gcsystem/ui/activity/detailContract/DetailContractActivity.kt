package com.brmsdi.gcsystem.ui.activity.detailContract

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.brmsdi.gcsystem.data.constants.Constant.CONTRACT.CONTRACT_DATA
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.ActivityDetailContractBinding
import com.brmsdi.gcsystem.ui.utils.LoadData

class DetailContractActivity : AppCompatActivity(), LoadData {
    private lateinit var binding: ActivityDetailContractBinding
    private var contract: Contract? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailContractBinding.inflate(layoutInflater)
        contract = load(intent.getBundleExtra(CONTRACT_DATA), CONTRACT_DATA, Contract::class.java)
        web()
        setContentView(binding.root)
    }

    private fun web() {
        val web = binding.webContract
        web.webViewClient = WebViewClient()
        web.loadUrl("file:///android_asset/contract.html")
    }
}