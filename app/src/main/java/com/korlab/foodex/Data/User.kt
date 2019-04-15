package com.korlab.foodex.Data

class User {

    lateinit var phone: String

    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var middleName: String

    var gender: Boolean = true
    var weight: Int = 60
    var weightMetrics: Boolean = false
    var growth: Int = 160
    var growthMetrics: Boolean = false

    var birthdayDay: Int = 0
    var birthdayMonth: Int = 0
    var birthdayYear: Int = 0
    var email: String = ""
    var note: String = ""
    var weekdaysAddress: Address? = null
    var weekendsAddress: Address? = null
    var deliveryType: Int = 0

}