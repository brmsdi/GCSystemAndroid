package com.brmsdi.gcsystem.data.listeners

import androidx.appcompat.widget.SearchView


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface OnSearchViewListener {
    fun addSearchListener(listener : SearchView.OnQueryTextListener)
}