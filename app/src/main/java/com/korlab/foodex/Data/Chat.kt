package com.korlab.foodex.Data

import com.korlab.foodex.Technical.Helper
import java.util.Date

class Chat(name: String, subject: String, dateLastMessage: Date, previewMessage: String, image: Int, type: Message.Sender) {


    var name: String? = null
    var subject: String? = null
    var dateLastMessage: Date? = null
    var previewMessage: String? = null
    var countUnreadMessage: Int = 0
    var image: Int? = null
    var messages: List<Message>? = null
        set(messages) {
            for (i in 0..messages!!.size - 1) {
                Helper.logObjectToJson(messages.get(i))
                if (!messages.get(i).isRead)
                    countUnreadMessage++
            }
            field = messages
        }
    var type: Message.Sender? = null

    init {
        this.name = name
        this.subject = subject
        this.dateLastMessage = dateLastMessage
        this.previewMessage = previewMessage
        this.image = image
        this.type = type

    }

    fun setReadAll() {
        Helper.log("setReadAll")
        for (i in 0..messages!!.size - 1) {
            messages!!.get(i).isRead = true
            Helper.log(messages!!.get(i).text + " isRead "+messages!!.get(i).isRead)
        }
        countUnreadMessage = 0
    }

}