package com.korlab.foodex.Data

import android.graphics.Bitmap

class Program(name: String, smallDescription: String, image: Bitmap) {
    var name: String = ""
    var smallDescription: String = ""
    var image: Bitmap? = null

    var price: Int = 0

    init {
        this.name = name
        this.smallDescription = smallDescription
        this.image = image
    }
}