package com.brmsdi.gcsystem.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class DateUtils private constructor() {
    companion object {
        fun dateFormattedToView(date: Date): String {
            val format = "dd/MM/yyyy HH:mm"
            val formatter = SimpleDateFormat(format, Locale("pt", "BR"))
            return formatter.format(date)
        }
    }
}