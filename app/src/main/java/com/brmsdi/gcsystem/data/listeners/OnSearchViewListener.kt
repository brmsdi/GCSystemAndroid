package com.brmsdi.gcsystem.data.listeners

import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.SearchView


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

interface OnSearchViewListener {
    fun addSearchListener(listener : SearchView.OnQueryTextListener)

    fun onFocusSearchView(searchView: SearchView)

    fun isSearchable(menuItem: MenuItem, destinationID: Int, searchable: Set<Int>) {
        menuItem.isVisible = searchable.contains(destinationID)
    }
}