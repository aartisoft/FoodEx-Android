package com.korlab.foodex.Data

import java.sql.Date

class Message(sender: Sender, date: Date, text: String) {


    var sender: Sender? = null
    var date: Date? = null
    var text: String? = null

    init {
        this.sender = sender
        this.date = date
        this.text = text
    }

    enum class Sender(val time: String) {
        MANAGER("manager"),
        CLIENT("client"),
        BOT("bot"),
    }
}