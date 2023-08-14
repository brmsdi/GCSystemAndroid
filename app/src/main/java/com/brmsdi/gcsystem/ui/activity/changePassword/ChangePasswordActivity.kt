package com.brmsdi.gcsystem.ui.activity.changePassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.databinding.ActivityChangePasswordBinding
import com.brmsdi.gcsystem.ui.fragment.sendEmail.SendEmailFragment

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.label_change_password)
        setSupportActionBar(toolbar)
        replaceFragment(SendEmailFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}