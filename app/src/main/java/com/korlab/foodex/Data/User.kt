package com.korlab.foodex.Data

import java.util.Date

class User {


    var email: String = ""
    var phoneNumber: String = ""
    var gender: Boolean = true
    var birthday: Date? = null
    var registration: Date? = null

    lateinit var name: Name
    lateinit var weight: Weight
    lateinit var growth: Growth


    var weekdaysAddress: Address? = null
    var weekendsAddress: Address? = null
    var deliveryType: Int = 0

    var note: String = ""


}