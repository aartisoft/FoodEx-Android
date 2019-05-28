package com.korlab.foodex.Data

class Meal(dayTime: Type, dishList: MutableList<Dish>) {
    var dayTime: Type? = null
    var dishList: List<Dish>? = null
//    var calories: Int = 0

//    var proteins: Int = 0
//    var fats: Int = 0
//    var carbo: Int = 0

    init {
        this.dayTime = dayTime
        this.dishList = dishList
//        this.calories = calories
//        this.proteins = proteins
//        this.fats = fats
//        this.carbo = carbo
    }
    enum class Type(val time: String, val intType: Int) {
        BREAKFAST("8:00", 0),
        BRUNCH("10:00", 1),
        LUNCH("12:00", 2),
        AFTERNOONMEALS("15:00", 3),
        SECONDAFTERNOONMEALS("17:00", 4),
        DINNER("19:00", 5);
        companion object {
            fun getType(index: Int): Type {
                for (l in Type.values()) {
                    if (l.intType === index) return l
                }
                throw IllegalArgumentException("Type not found.")
            }
        }
    }
}