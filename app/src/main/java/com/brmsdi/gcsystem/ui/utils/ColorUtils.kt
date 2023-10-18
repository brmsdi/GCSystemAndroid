package com.brmsdi.gcsystem.ui.utils

import android.content.res.Resources
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brmsdi.gcsystem.R


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ColorUtils private constructor() {

    companion object {
        fun getColorSwipeRefreshLayout(resources: Resources, view: SwipeRefreshLayout) {
            view.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.pink_1, resources.newTheme()))
            view.setColorSchemeColors(resources.getColor(R.color.white, resources.newTheme()))
        }
    }
}