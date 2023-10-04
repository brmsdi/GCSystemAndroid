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
import android.view.View.GONE
import android.view.View.OnClickListener
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant.AUTH.TOKEN
import com.brmsdi.gcsystem.data.constants.Constant.CONTRACT.CONTRACT_ID
import com.brmsdi.gcsystem.data.constants.Constant.ENDPOINT.CONTRACT_DETAIL
import com.brmsdi.gcsystem.data.constants.Constant.HTTP.Bearer
import com.brmsdi.gcsystem.data.constants.Constant.HTTP.HEADER_AUTHORIZATION
import com.brmsdi.gcsystem.data.constants.Constant.REQUESTCODE.WRITE_EXTERNAL_STORAGE
import com.brmsdi.gcsystem.data.security.SecurityPreferences
import com.brmsdi.gcsystem.databinding.ActivityDetailContractBinding
import com.brmsdi.gcsystem.ui.utils.LoadData

class DetailContractActivity : AppCompatActivity(), LoadData, OnClickListener {
    private lateinit var binding: ActivityDetailContractBinding
    private var contractID: Int = 0
    private lateinit var webContract: WebView
    private var isLoading = true
    private lateinit var securityPreferences: SecurityPreferences

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE
            )
        }
        binding = ActivityDetailContractBinding.inflate(layoutInflater)
        securityPreferences = SecurityPreferences(this)
        contractID = intent.getIntExtra(CONTRACT_ID, 0)
        getContract()
        addAction()
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissions.forEachIndexed { index, value ->
            when(value) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    if (grantResults[index] != 0) {
                        binding.floatingPrintout.visibility = GONE
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.floatingPrintout.id -> generatePdfFromWebView(webContract)
        }
    }

    private fun getContract() {
        if (contractID <= 0) return
        val token = securityPreferences.get(TOKEN)
        if (token.isEmpty()) return
        webContract = binding.webContract
        webContract.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isLoading = false
            }
        }
        webContract.loadUrl(
            "$CONTRACT_DETAIL?id=$contractID",
            hashMapOf(HEADER_AUTHORIZATION to "$Bearer $token")
        )
    }

    private fun generatePdfFromWebView(webView: WebView) {
        if (isLoading) return
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val idDocument = contractID
        val jobName = String.format("%s_%d", getString(R.string.app_name), idDocument)
        val printAdapter = webView.createPrintDocumentAdapter(jobName)
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }

    private fun addAction() {
        binding.floatingPrintout.setOnClickListener(this)
    }
}