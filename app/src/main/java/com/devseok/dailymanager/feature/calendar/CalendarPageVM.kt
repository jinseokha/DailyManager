package com.devseok.dailymanager.feature.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.CalendarData
import com.devseok.dailymanager.data.DailyManagerRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarPageVM @Inject constructor(
    private val repository: DailyManagerRepository,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firestore: FirebaseFirestore,
): ViewModel() {

    fun addMessageToUser(data: CalendarData, onResult: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Log.e("Firestore", "User not logged in")
            return
        }

       /* val data = mapOf(
            "text" to "test",
            "timestamp" to FieldValue.serverTimestamp()
        )

        firestore.collection("calendar")
            .document(uid)
            .collection("messages")
            .add(data)
            .addOnSuccessListener {
                Log.d("Firestore", "Message added to user 'a'")
            }
            .addOnFailureListener {
                Log.e("Firestore", "Failed to add message", it)
            }*/


        firestore.collection("calendar")
            .document(uid)
            .collection("messages")
            .add(data)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun getMessage(userId: String, date: String, onResult: (List<CalendarData>) -> Unit) {

        firestore.collection("calendar")
            .whereEqualTo("userId", userId)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { result ->
                val dataList = result.mapNotNull { doc ->
                    doc.toObject(CalendarData::class.java)
                }
                onResult(dataList)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(emptyList())
            }
    }

}