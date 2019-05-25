package com.korlab.garage.sharedcode.fireserver

import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


object Database {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    init {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    fun writeValue(path: String, value: Any) {
        Log.d("FireBase", "Database: $database")
        Log.d("FireBase", "path: $path")
        Log.d("FireBase", "value: $value")
        database.getReference(path).setValue(value)
    }

    fun readValue(path: String, onSuccess: (dataSnapshot: DataSnapshot) -> Unit) {
        database.getReference(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onSuccess(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("KORLAB_DEBUG", "cancel")
            }
        })
    }

    fun subscribeOnChanges(pathWithKey: String, onSuccess: (dataSnapshot: DataSnapshot) -> Unit) {
        val myRef = database.getReference(pathWithKey)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    onSuccess(dataSnapshot)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("KORLAB_DEBUG", "cancel")
            }
        })
    }

    // todo delete this please))))
    fun subscribeOnChangesAchievements(pathWithKey: String, onSuccess: (dataSnapshot: DataSnapshot) -> Unit) {
        database.getReference(pathWithKey).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { onSuccess(dataSnapshot) }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}