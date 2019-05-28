package com.korlab.foodex.Data

import android.graphics.Bitmap

class Program(name: String, smallDescription: String, image: String, link: String) {
    var name: String = ""
    var smallDescription: String = ""
    var image: String? = null
    var link: String? = null

    var price: Int = 0

    init {
        this.name = name
        this.smallDescription = smallDescription
        this.image = image
        this.link = link
    }
}