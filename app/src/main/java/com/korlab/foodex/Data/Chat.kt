package com.korlab.foodex.Data

import java.sql.Date

class Chat(name: String, subject: String, dateLastMessage: Date, previewMessage: String, countUnreadMessage: Int, image: Int, type: Message.Sender) {


    var name: String? = null
    var subject: String? = null
    var dateLastMessage: Date? = null
    var previewMessage: String? = null
    var countUnreadMessage: Int? = 0
    var image: Int? = null
    var messages: List<Message> = ArrayList()
    var type: Message.Sender? = null

    init {
        this.name = name
        this.subject = subject
        this.dateLastMessage = dateLastMessage
        this.previewMessage = previewMessage
        this.countUnreadMessage = countUnreadMessage
        this.image = image
        this.type = type
    }
}