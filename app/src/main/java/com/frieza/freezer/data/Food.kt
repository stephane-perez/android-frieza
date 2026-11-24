package com.frieza.freezer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frieza.freezer.R

@Entity(tableName = "freezers")
data class Freezer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val position: Int = 0
)

/** A drawer (shelf) of a freezer. [position] 0 is the top drawer. */
@Entity(tableName = "drawers")
data class Drawer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val freezerId: Long,
    val name: String,
    val position: Int = 0
)

/** User-editable category. [color] is an ARGB value (e.g. 0xFF66BB6A). */
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val color: Long,
    val position: Int = 0
)

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val drawerId: Long,
    val categoryIds: List<Long> = emptyList(),
    val unitCount: Int? = null,
    val weightGrams: Int? = null,
    val portions: Int? = null,
    /** Freezing date, stored as UTC midnight (see util/Dates.kt). */
    val freezeDate: Long? = null,
    /** Best-before date, stored as UTC midnight. */
    val expirationDate: Long? = null,
    val notes: String = "",
    val dateAdded: Long = System.currentTimeMillis()
)

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val checked: Boolean = false
)

/**
 * Categories created on first launch (same set as the first version of the app).
 * Names are seeded in the language active at that time; existing names are never translated.
 */
object DefaultCategories {
    val all: List<Pair<Int, Long>> = listOf(
        R.string.default_category_vegetables to 0xFF66BB6AL,
        R.string.default_category_fruits to 0xFF9CCC65L,
        R.string.default_category_meals to 0xFF7E57C2L,
        R.string.default_category_meat to 0xFFE57373L,
        R.string.default_category_fish to 0xFFEC7FA9L,
        R.string.default_category_condiments to 0xFFFFA726L,
        R.string.default_category_bread to 0xFF8D6E63L,
        R.string.default_category_desserts to 0xFF26A69AL,
        R.string.default_category_other to 0xFF78909CL
    )
}
