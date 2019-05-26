package com.korlab.foodex.Data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.korlab.foodex.FireServer.Database
import com.korlab.foodex.Technical.Helper
import java.util.Date

class User {


    var email: String = ""
    var phoneNumber: String = ""

    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var middleName: String

    var gender: Boolean = true

    var weight: Int = 60
    var weightMetrics: Boolean = false
    var growth: Int = 160
    var growthMetrics: Boolean = false

    var birthday: Date? = null
//    var birthdayDay: Int = 0
//    var birthdayMonth: Int = 0
//    var birthdayYear: Int = 0

    var weekdaysAddress: Address? = null
    var weekendsAddress: Address? = null
    var deliveryType: Int = 0

    var note: String = ""

    companion object {
        fun getData(onSuccess: (user: User) -> Unit, onFail: () -> Unit) {
            // todo move to one function
            Helper.log("Get customer" + FirebaseAuth.getInstance().currentUser!!.uid)
            Database.readValue("customers", FirebaseAuth.getInstance().currentUser!!.uid) { documentSnapshot: DocumentSnapshot? ->
//            Database.readValue("customers", "3jw1e2dVImX4P01K45Dlz4govWm1") { documentSnapshot: DocumentSnapshot? ->
                val userData = documentSnapshot?.data as HashMap<*, *>?
                if (userData == null) {
                    onFail()
                } else {
                    Helper.log("Response from FireStore " + documentSnapshot?.data!!)
                    val name = userData["name"] as HashMap<*, *>?

                    val user = User()
                    user.phoneNumber = userData["phoneNumber"]?.toString() ?: ""
                    user.firstName = name!!["first"]?.toString() ?: ""
                    user.lastName = name!!["last"]?.toString() ?: ""
                    user.middleName = name!!["middle"]?.toString() ?: ""
                    val date = userData!!["birthday"] as com.google.firebase.Timestamp
                    user.birthday = date.toDate()
                    Helper.log("birthday Year: " + user.birthday?.year)
                    Helper.log("birthday Month: " + user.birthday?.month)
                    Helper.log("birthday Date: " + user.birthday?.date)
                    Helper.logObjectToJson(userData)
                    onSuccess(user)
                }
            }
        }
    }
}