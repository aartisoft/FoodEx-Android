package com.korlab.foodex.Data

import android.graphics.Bitmap
import java.sql.Date

class Promo(id: Int, name: String, date: Date, image: String) {

    var id: Int = 0
    var name: String = ""
    var date: Date? = null
    var image: String? = null

    init {
        this.id = id
        this.name = name
        this.date = date
        this.image = image
    }
}