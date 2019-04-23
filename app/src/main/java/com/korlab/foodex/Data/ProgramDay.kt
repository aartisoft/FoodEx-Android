package com.korlab.foodex.Data

import java.sql.Date

class ProgramDay(date: Date, meals: List<Meal>) {
    var date: Date? = null
    var meals: List<Meal>? = null

    init {
        this.date = date
        this.meals = meals
    }
}