package com.example.easyfood.data.pojo

import androidx.room.Entity


@Entity(tableName = "favorites")
data class MealsByCategory(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)