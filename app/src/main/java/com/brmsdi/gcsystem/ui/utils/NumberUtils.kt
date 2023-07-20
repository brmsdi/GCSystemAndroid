package com.brmsdi.gcsystem.ui.utils

import android.content.Context
import android.os.Build
import java.text.NumberFormat
import java.util.Locale


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class NumberUtils private constructor() {

    companion object {
        fun formatCoin(locale: Locale, value: Double, maximumFractionDigits: Int = 3, minimumFractionDigits: Int = 2) : String {
            val numberFormat = NumberFormat.getNumberInstance(locale)
            numberFormat.maximumFractionDigits = maximumFractionDigits
            numberFormat.minimumFractionDigits = minimumFractionDigits
            val symbol = numberFormat.currency?.symbol
            return String.format("%s %s", symbol, numberFormat.format(value))
        }

        @Suppress("DEPRECATION")
        fun getSystemLocale(context: Context) : Locale {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) context.resources.configuration.locales[0]
            else context.resources.configuration.locale
        }
    }
}