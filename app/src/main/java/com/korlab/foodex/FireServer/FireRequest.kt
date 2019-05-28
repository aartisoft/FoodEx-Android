package com.korlab.foodex.FireServer

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.functions.FirebaseFunctions
import com.korlab.foodex.Technical.Helper
import org.json.JSONObject

class FireRequest {


    companion object {
        private var functions: FirebaseFunctions = FirebaseFunctions.getInstance()

        fun getData(collectionName: String, documentId: String, onSuccess: (hashMap: HashMap<String, Object>) -> Unit, onFail: () -> Unit) {
            Helper.log("Get documentId: " + documentId + " from collection: " + collectionName)
            Database.readValue(collectionName, documentId) { documentSnapshot: DocumentSnapshot? ->
                val hashMap = documentSnapshot?.data as HashMap<String, Object>?
                if (hashMap == null) {
                    onFail()
                } else {
                    Helper.log("Response from FireStore " + documentSnapshot?.data!!)
//                    val name = userData["name"] as HashMap<*, *>?
//                    val user = User()
//                    user.phoneNumber = userData["phoneNumber"]?.toString() ?: ""
//                    user.firstName = name!!["first"]?.toString() ?: ""
//                    user.lastName = name!!["last"]?.toString() ?: ""
//                    user.middleName = name!!["middle"]?.toString() ?: ""
//                    val date = userData!!["birthday"] as com.google.firebase.Timestamp
//                    user.birthday = date.toDate()
//                    Helper.log("birthday Year: " + user.birthday?.year)
//                    Helper.log("birthday Month: " + user.birthday?.month)
//                    Helper.log("birthday Date: " + user.birthday?.date)
//                    Helper.logObjectToJson(userData)
                    onSuccess(hashMap)
                }
            }
        }

        fun callFunction(functionName: String, dataHashMap: java.util.HashMap<String, Any>, onSuccess: (hashMap: HashMap<String, Object>) -> Unit, onFail: () -> Unit): Task<String> {
            Helper.log("Call function: $functionName")
            Helper.log("Send data: $dataHashMap")
//            val data = java.util.HashMap<String, Any>()
//            data["isWorker"] = isWorker
            return functions
                    .getHttpsCallable(functionName)
                    .call(dataHashMap)
                    .continueWith { task ->
                        Helper.log("continueWith")
                        try {
                            val hashMap = task.result!!.data as HashMap<String, Object>?

//                            val hashMap: java.util.HashMap<String, String> = task.result!!.data as java.util.HashMap<String, String>
                            Helper.log("hashMap: $hashMap")
                            val obj = JSONObject(hashMap)
                            val code: Int = obj.getString("code").toInt()
                            val text = obj.getString("text")
//                            val exists = obj.getBoolean("exists")
                            Helper.log("code: $code text: $text")
                            if (code == 200) {
                                onSuccess(hashMap!!)
                            } else {
                                onFail()
                            }
                        } catch (e: Exception) {
                            Helper.log("Error callFunction: $e")
                            onFail()
                        }
                        "0"
                    }
        }
    }
}