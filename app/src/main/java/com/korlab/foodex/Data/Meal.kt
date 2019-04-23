package com.korlab.foodex.Data

class Meal(dayTime: Int, dishList: List<Dish>) {
    var dayTime: Int = 0
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
}