package com.korlab.foodex.Data

class Dish(name: String, type: Int, calorie: Int, proteins: Int, fats: Int, carbo: Int) {
    var name: String = ""
    var dishType: Int = 0
    var calories: Int = 0

    var proteins: Int = 0
    var fats: Int = 0
    var carbo: Int = 0

    init {
        this.name = name
        this.dishType = type
        this.calories = calorie
        this.proteins = proteins
        this.fats = fats
        this.carbo = carbo
    }
}