package com.frieza.freezer.util

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/*
 * Dates picked by the user (freezing / best-before) are stored as UTC midnight, which is exactly
 * what Material's DatePicker returns. They must therefore always be formatted in UTC.
 */

private val UTC: TimeZone = TimeZone.getTimeZone("UTC")

/** Numeric date in the phone's language order (e.g. 24/11/2026 in French, 11/24/2026 in US English). */
fun formatDate(millis: Long): String {
    val locale = Locale.getDefault()
    val pattern = DateFormat.getBestDateTimePattern(locale, "ddMMyyyy")
    return SimpleDateFormat(pattern, locale).apply { timeZone = UTC }.format(Date(millis))
}

/** Today's date (in the phone's time zone) expressed as UTC midnight. */
fun todayUtcMillis(): Long {
    val local = Calendar.getInstance()
    val utc = Calendar.getInstance(UTC)
    utc.clear()
    utc.set(local.get(Calendar.YEAR), local.get(Calendar.MONTH), local.get(Calendar.DAY_OF_MONTH))
    return utc.timeInMillis
}

fun addMonthsUtc(fromMillis: Long, months: Int): Long {
    val cal = Calendar.getInstance(UTC)
    cal.timeInMillis = fromMillis
    cal.add(Calendar.MONTH, months)
    return cal.timeInMillis
}

/** Whole days from today until [dateMillis] (negative when past). */
fun daysUntil(dateMillis: Long): Long =
    TimeUnit.MILLISECONDS.toDays(dateMillis - todayUtcMillis())

fun toIsoDate(millis: Long): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.US).apply { timeZone = UTC }.format(Date(millis))

fun formatWeight(grams: Int): String =
    if (grams < 1000) "$grams g"
    else {
        val kg = grams / 1000.0
        "%.2f".format(Locale.getDefault(), kg).trimEnd('0').trimEnd(',', '.') + " kg"
    }
