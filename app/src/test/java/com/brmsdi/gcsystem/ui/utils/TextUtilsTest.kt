package com.brmsdi.gcsystem.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Test


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class TextUtilsTest {

    @Test
    fun assembleCode_success() {
        val code1 = "1"
        val code2 = "2"
        val code3 = "3"
        val code4 = "4"
        val code5 = "5"
        val code6 = "6"
        val correct = "123456"
        val result = TextUtils.assembleCode(code1, code2, code3, code4, code5, code6)
        assertEquals(correct, result.toString())
    }
}