package com.brmsdi.gcsystem.ui.utils

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */

class TextUtils {

    companion object {
        fun fieldsIsNotEmpty(vararg fields : String) : Boolean {
            if (fields.isEmpty()) return false
            for (field in fields) {
                if (field.isEmpty()) return false
            }
            return true
        } // END fieldsIsNotEmpty

        fun displayMessage(context: Context, message : String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        /**
         * Limitar a quantidade de caracteres em um EditText
         * @param editText Componente que o filtro será aplicado
         * @param maxLength limite de caractere
         */
        fun setMaxLength(editText: EditText, maxLength: Int) {
            val inputFilter = InputFilter { source, start, end, dest, _, _ ->
                val inputLength = dest?.length ?: 0
                val remainingLength = maxLength - inputLength

                if (remainingLength <= 0) {
                    ""
                } else if (remainingLength >= end - start) {
                    // Accept the new input
                    null
                } else {
                    // Truncate the new input to fit within the limit
                    val truncatedInput = source?.subSequence(start, start + remainingLength)
                    truncatedInput
                }
            }

            val filters = arrayOf(inputFilter)
            editText.filters = filters
        } // END setMaxLength

        fun haveMinimumSize(text: String, min: UInt): Boolean {
            return text.length >= min.toInt()
        }

        fun cpfIsValid(value : String) : Boolean {
            return value.length == 11
        }

        fun jsonToString(value: String): String {
            return Gson().fromJson(value, String::class.java)
        }

        fun <T> jsonToObject(value : String, c : Class<T>): T {
             return Gson().fromJson(value, c)
        }

        fun assembleCode(vararg codes: String): StringBuilder {
            val code = StringBuilder("")
            codes.forEach {
                code.append(it)
            }
            return code
        }
    }
}