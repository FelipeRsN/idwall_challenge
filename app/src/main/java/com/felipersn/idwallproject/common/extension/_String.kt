package com.felipersn.idwallproject.common.extension

import android.util.Patterns

fun String.validateMailAddress(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}