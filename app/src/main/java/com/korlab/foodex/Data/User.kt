package com.korlab.foodex.Data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.korlab.foodex.Technical.Helper
import com.korlab.garage.sharedcode.fireserver.Database

class User {


    var email: String = ""
    var phone: String = ""

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

    var weekdaysAddress: Address? = null
    var weekendsAddress: Address? = null
    var deliveryType: Int = 0

    var note: String = ""

    companion object {
        fun getData(onSuccess: (user: User) -> Unit, onFail: () -> Unit) {
            // todo move to one function
            Helper.log("get users/" + FirebaseAuth.getInstance().currentUser!!.uid)
            Database.readValue("users/" + FirebaseAuth.getInstance().currentUser!!.uid) { dataSnapshot: DataSnapshot ->
                Helper.log("response from server " + dataSnapshot.value)
                val userData = dataSnapshot.value as HashMap<*, *>?
                if (userData == null) {
                    onFail()
                    null
                } else {
                    val user = User()
                    user.phone = userData["phone"]?.toString() ?: ""
                    user.firstName = userData["firstName"]?.toString() ?: ""
                    user.lastName = userData["lastName"]?.toString() ?: ""
                    user.middleName = userData["middleName"]?.toString() ?: ""
                    Helper.log("userData " + userData.toString())
                    onSuccess(user)
                }
            }
        }
    }
}