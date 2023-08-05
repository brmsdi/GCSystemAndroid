package com.brmsdi.gcsystem.ui.activity.detailContract

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.view.View
import android.view.View.OnClickListener
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.CONTRACT.CONTRACT_DATA
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.ActivityDetailContractBinding
import com.brmsdi.gcsystem.ui.utils.LoadData

class DetailContractActivity : AppCompatActivity(), LoadData, OnClickListener {
    private lateinit var binding: ActivityDetailContractBinding
    private var contract: Contract? = null
    private lateinit var webContract : WebView
    private var isLoading = true

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//        }
        binding = ActivityDetailContractBinding.inflate(layoutInflater)
        contract = load(intent.getBundleExtra(CONTRACT_DATA), CONTRACT_DATA, Contract::class.java)
        getContract()
        addAction()
        setContentView(binding.root)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.floatingPrintout.id -> generatePdfFromWebView(webContract)
        }
    }

    private fun getContract() {
        webContract = binding.webContract
        webContract.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isLoading = false
            }
        }
        webContract.loadUrl("file:///android_asset/contract.html")
    }

    private fun generatePdfFromWebView(webView: WebView) {
        if (isLoading) return
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val idDocument = contract?.id ?: 0
        val jobName = String.format("%s_%d", getString(R.string.app_name), idDocument)
        val printAdapter = webView.createPrintDocumentAdapter(jobName)
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }

    private fun addAction() {
        binding.floatingPrintout.setOnClickListener(this)
    }
}