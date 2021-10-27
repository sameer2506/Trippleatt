package com.example.trippleatt.security

import java.util.regex.Pattern

fun isValidMail(email: String): Boolean {
    val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    return Pattern.compile(EMAIL_STRING).matcher(email).matches()
}

fun isValidMobile(phone: String): Boolean {
    return if (!Pattern.matches("^[+91][0-9]{10}$", phone)) {
        phone.length == 10
    } else false
}