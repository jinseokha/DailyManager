package com.devseok.dailymanager.feature.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.CalendarData
import com.devseok.dailymanager.data.DailyManagerRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarPageVM @Inject constructor(
    private val repository: DailyManagerRepository,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firestore: FirebaseFirestore,
): ViewModel() {

    val _saveDataList: MutableStateFlow<Map<LocalDate, List<CalendarData>>> = MutableStateFlow(emptyMap())
    val saveDataList = _saveDataList.asStateFlow()

    fun delMessage(data: CalendarData, onResult: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Log.e("Firestore", "User not logged in")
            return
        }

        firestore.collection("calendar")
            .document(uid)
            .collection("messages")
            .document(data.id)
            .delete()
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

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
            .add(
                mapOf(
                    "userId" to data.userId,
                    "date" to data.date,
                    "message" to data.message,
                    "timestamp" to FieldValue.serverTimestamp()
                )
            )
            .addOnSuccessListener { it ->
                onResult(true)
            }
            .addOnFailureListener { it ->
                onResult(false)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllMessage(onResult: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Log.e("Firestore", "User not logged in")
            return
        }

        firestore.collection("calendar")
            .document(uid)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->

                //val result = result.documents.mapNotNull { it.toObject(CalendarData::class.java) }

                val list = result.documents.mapNotNull { doc ->
                    doc.toObject(CalendarData::class.java)?.copy(id = doc.id)
                }
                val grouped = list.groupBy { LocalDate.parse(it.date) }
                // LocalDate 기준으로 메시지 그룹화
               /* val map = result.groupBy(
                    { LocalDate.parse(it.date) }, // key: LocalDate
                    { it.message }                // value: message string
                )*/

                _saveDataList.value = grouped
                onResult(true)

               /* val dataList = result.toObjects(CalendarData::class.java)
                _saveDataList.value = dataList*/
                //onResult(dataList)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(false)
               // onResult(emptyList())
            }
    }


    /*fun getMessage(userId: String, date: String, onResult: (List<CalendarData>) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            Log.e("Firestore", "User not logged in")
            return
        }

        firestore.collection("calendar")
            .document(uid)
            .collection("messages")
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { result ->
                val dataList = result.toObjects(CalendarData::class.java)

                _saveDataList.value = dataList
                onResult(dataList)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(emptyList())
            }
    }*/

}