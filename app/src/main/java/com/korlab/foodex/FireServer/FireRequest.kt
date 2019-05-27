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

        fun getData(collectionName: String, documentId: String, onSuccess: (hashMap: HashMap<*, *>) -> Unit, onFail: () -> Unit) {
            Helper.log("Get documentId: " + documentId + " from collection: " + collectionName)
            Database.readValue(collectionName, documentId) { documentSnapshot: DocumentSnapshot? ->
                val hashMap = documentSnapshot?.data as HashMap<*, *>?
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
        fun pingStartApp(isWorker: Boolean): Task<String> {
            Log.d("UIDebug", "pingStartApp()")
            val data = java.util.HashMap<String, Any>()
            data["isWorker"] = isWorker

            return functions
                    .getHttpsCallable("pingStartApp")
                    .call(data)
                    .continueWith { task ->
                        Log.d("UIDebug", "continueWith")
                        try {
                            val result: java.util.HashMap<String, String> = task.result!!.data as java.util.HashMap<String, String>
                            Log.d("UIDebug", "result: $result")
                            val obj = JSONObject(result)
                            Log.d("UIDebug", "code: " + obj.getString("code") + " text: " + obj.getString("text"))
                        } catch (e: Exception) {
                            Log.d("UIDebug", "error: $e")
                        }
                        "0"
                    }
        }
        fun callFunction(functionName: String, dataHashMap: java.util.HashMap<String, Any>, onSuccess: () -> Unit, onFail: () -> Unit): Task<String> {
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
                            val result: java.util.HashMap<String, String> = task.result!!.data as java.util.HashMap<String, String>
                            Helper.log("result: $result")
                            val obj = JSONObject(result)
                            val code: Int = obj.getString("code").toInt()
                            val text = obj.getString("text")
                            Helper.log("code: $code text: $text")
                            if (code == 500) {
                                onSuccess()
                            } else {
                                onFail()
                            }
                        } catch (e: Exception) {
                            Helper.log("Error: $e")
                            onFail()
                        }
                        "0"
                    }
        }
    }
}