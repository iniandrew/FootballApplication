package com.andrew.footballapplication

import android.view.View
import android.widget.ProgressBar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun showLoading(state: Boolean, progressBar: ProgressBar) {
    if (state) {
        progressBar.visible()
    } else {
        progressBar.gone()
    }
}

fun formatDate(date: String, format: String, isDate: Boolean): String {
    val old = SimpleDateFormat(if (isDate) "yyyy-MM-dd" else "HH:mm:ss", Locale.US)

    var result = ""

    try {
        val oldDate = old.parse(date)
        val newFormat = SimpleDateFormat(format, Locale.US)

        result = newFormat.format(oldDate!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return result
}

fun getDateFormat(date: String?): String {
    return formatDate(date.toString(), "EEE, dd MMM yyyy", true)
}

fun getTimeFormat(date: String?): String {
    return formatDate(date.toString(), "HH:mm", false)
}