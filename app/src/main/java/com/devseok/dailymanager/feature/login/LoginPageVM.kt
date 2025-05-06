package com.devseok.dailymanager.feature.login

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devseok.dailymanager.data.DailyManagerRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginPageVM @Inject constructor(
    private val repository: DailyManagerRepository,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firestore: FirebaseFirestore,
): ViewModel() {

    private val _fireBaseUserInfo: MutableStateFlow<FirebaseUser?> = MutableStateFlow(FirebaseAuth.getInstance().currentUser)
    val fireBaseUserInfo: StateFlow<FirebaseUser?> = _fireBaseUserInfo.asStateFlow()

    private val _userProfile = MutableStateFlow<Map<String, Any>?>(null)
    val userProfile: StateFlow<Map<String, Any>?> = _userProfile.asStateFlow()

    init {
        _fireBaseUserInfo.value = firebaseAuth.currentUser
    }

    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    val userInfo = firebaseAuth.currentUser
                    _fireBaseUserInfo.value = userInfo

                    fireBaseUserInfo.let {
                        saveUserToFirestore(it.value!!)
                    }
                }
                .addOnFailureListener {
                    Log.e("AuthViewModel", "Firebase sign in failed", it)
                }

        } catch (e: ApiException) {
            Log.e("AuthViewModel", "Google sign in failed", e)
        }

    }

    private fun saveUserToFirestore(userInfo: FirebaseUser) {
        val userMap = mapOf(
            "uid" to userInfo.uid,
            "name" to (userInfo.displayName ?: ""),
            "email" to (userInfo.email ?: ""),
            "photoUrl" to (userInfo.photoUrl?.toString() ?: ""),
            "timestamp" to FieldValue.serverTimestamp()
        )

        firestore.collection("users").document(userInfo.uid)
            .set(userMap, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firestore", "User data saved.")
                fetchUserProfile(userInfo.uid)
            }
            .addOnFailureListener {
                Log.e("Firestore", "Error saving user data", it)
            }
    }

    private fun fetchUserProfile(uid: String) {
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()){
                    _userProfile.value = doc.data
                }
            }
            .addOnFailureListener {
                Log.e("Firestore", "Error fetching user profile", it)
            }
    }

}