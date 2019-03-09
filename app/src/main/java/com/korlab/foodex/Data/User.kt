package com.korlab.foodex.Data

class User {

    lateinit var phone: String

    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var middleName: String

    var isMale: Boolean = true
    var weight: Int = 60
    var weightMetrics: Boolean = false
    var growth: Int = 160
    var growthMetrics: Boolean = false


    companion object {
        const val TAG = "User"
    }
}