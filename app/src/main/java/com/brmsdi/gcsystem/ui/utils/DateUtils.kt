package com.brmsdi.gcsystem.ui.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
            val format = "dd/MM/yyyy"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern(format)
                val localDate = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                return formatter.format(localDate)
            }
            val formatter = SimpleDateFormat(format, Locale("pt", "BR"))
            return formatter.format(date)
        }
    }
}