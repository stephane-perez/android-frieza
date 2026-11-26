package com.frieza.freezer.util

import android.content.res.Resources
import com.frieza.freezer.R
import com.frieza.freezer.data.Food

/** "2×, 1.5 kg, 4 portions" – only the parts that are filled in. */
fun quantityText(food: Food, res: Resources): String {
    val parts = mutableListOf<String>()
    food.unitCount?.let { parts += "$it×" }
    food.weightGrams?.let { parts += formatWeight(it) }
    food.portions?.let { parts += res.getQuantityString(R.plurals.portions_count, it, it) }
    return parts.joinToString(", ")
}

/** Short badge for foods that are expired or about to expire, null otherwise. */
fun expirationBadge(expirationDate: Long?, res: Resources): String? {
    if (expirationDate == null) return null
    val days = daysUntil(expirationDate)
    return when {
        days < 0 -> res.getString(R.string.badge_expired)
        days <= 30 -> res.getString(R.string.badge_under_1_month)
        days <= 60 -> res.getString(R.string.badge_under_2_months)
        else -> null
    }
}
