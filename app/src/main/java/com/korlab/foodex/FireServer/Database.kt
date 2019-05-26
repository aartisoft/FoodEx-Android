package com.korlab.foodex.FireServer

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.korlab.foodex.Technical.Helper


object Database {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
//        FirebaseFirestore.getInstance().setPersistenceEnabled(true)
    }

//    fun writeValue(path: String, value: Any) {
//        Log.d("FireBase", "Database: $database")
//        Log.d("FireBase", "path: $path")
//        Log.d("FireBase", "value: $value")
//        database.getReference(path).setValue(value)
//    }

    fun readValue(collection: String, documentId: String, onSuccess: (dataSnapshot: DocumentSnapshot?) -> Unit) {
        val docRef = database.collection(collection).document(documentId)
        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        onSuccess(document)
                        Helper.log("DocumentSnapshot data: ${document.data}")
                    } else {
                        Helper.log("No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Helper.log("Get failed with: $exception")
                }
    }

//    fun subscribeOnChanges(pathWithKey: String, onSuccess: (dataSnapshot: DataSnapshot) -> Unit) {
//        val myRef = database.getReference(pathWithKey)
//
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if(dataSnapshot.exists()) {
//                    onSuccess(dataSnapshot)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("KORLAB_DEBUG", "cancel")
//            }
//        })
//    }
//
//    // todo delete this please))))
//    fun subscribeOnChangesAchievements(pathWithKey: String, onSuccess: (dataSnapshot: DataSnapshot) -> Unit) {
//        database.getReference(pathWithKey).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) { onSuccess(dataSnapshot) }
//            override fun onCancelled(error: DatabaseError) {}
//        })
//    }
}