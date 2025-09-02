package com.devseok.dailymanager.feature.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.custom.picker.ColorEnvelopeDTO
import com.devseok.dailymanager.data.CalendarDataDTO
import com.devseok.dailymanager.data.DailyManagerRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

    val _saveDataList: MutableStateFlow<Map<LocalDate, List<CalendarDataDTO>>> = MutableStateFlow(emptyMap())
    val saveDataList = _saveDataList.asStateFlow()

    fun delMessage(data: CalendarDataDTO, onResult: (Boolean) -> Unit) {
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

    fun addMessageToUser(data: CalendarDataDTO, onResult: (Boolean) -> Unit) {
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
        val colorMap = mapOf(
            "colorInt" to data.color.color.toArgb(),
            "hexCode" to data.color.hexCode,
            "fromUser" to data.color.fromUser
        )

        firestore.collection("calendar")
            .document(uid)
            .collection("messages")
            .add(
                mapOf(
                    "userId" to data.userId,
                    "date" to data.date,
                    "message" to data.message,
                    "color" to colorMap,
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

                val list = result.documents.mapNotNull { doc ->
                    val dto = doc.toObject(CalendarDataDTO::class.java) ?: return@mapNotNull null

                    CalendarDataDTO(
                        id = doc.id,
                        userId = dto.userId,
                        date = dto.date,
                        message = dto.message,
                        color = ColorEnvelopeDTO(
                            colorInt = dto.color.colorInt,
                            hexCode = dto.color.hexCode,
                            fromUser = dto.color.fromUser
                        ),
                        timestamp = dto.timestamp
                    )
                }

                val grouped = list.groupBy { LocalDate.parse(it.date) }

                _saveDataList.value = grouped
                onResult(true)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(false)
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