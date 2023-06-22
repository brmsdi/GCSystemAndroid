package com.brmsdi.gcsystem.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
open class BaseFragment : Fragment() {

    fun replaceFragment(container: Int, fragment: Fragment, backStack: String? = null) {
        parentFragmentManager
            .beginTransaction()
            .replace(container, fragment)
            .addToBackStack(backStack)
            .commit()
    }
}