package com.devseok.dailymanager.feature.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarPageVM @Inject constructor(
    private val repository: DailyManagerRepository,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firestore: FirebaseFirestore,
): ViewModel() {

    fun addMessageToUser(message: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Log.e("Firestore", "User not logged in")
            return
        }

        val data = mapOf(
            "text" to message,
            "timestamp" to FieldValue.serverTimestamp()
        )

        firestore.collection("users")
            .document(uid)
            .collection("messages")
            .add(data)
            .addOnSuccessListener {
                Log.d("Firestore", "Message added to user 'a'")
            }
            .addOnFailureListener {
                Log.e("Firestore", "Failed to add message", it)
            }
    }

}