package com.brmsdi.gcsystem.ui.activity.changePassword

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.databinding.ActivityChangePasswordBinding
import com.brmsdi.gcsystem.ui.fragment.sendEmail.SendEmailFragment

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.pink_1)))
        _binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        replaceFragment(SendEmailFragment.newInstance())
        setContentView(_binding.root)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}