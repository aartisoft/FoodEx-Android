package com.korlab.foodex.Data

class Meal(name: String, calorie: Int, proteins: Int, fats: Int, carbo: Int) {
    var name: String = ""
    var calorie: Int = 0

    var proteins: Int = 0
    var fats: Int = 0
    var carbo: Int = 0

    init {
        this.name = name
        this.calorie = calorie
        this.proteins = proteins
        this.fats = fats
        this.carbo = carbo
    }
}