package com.devseok.dailymanager.data

import com.google.firebase.auth.FirebaseUser

object Utils {

    fun getUserProfile(userInfo: FirebaseUser): Map<String, String?> {
        return if (userInfo != null) {
            mapOf(
                "uid" to userInfo.uid,
                "name" to userInfo.displayName,
                "email" to userInfo.email,
                "photoUrl" to userInfo.photoUrl?.toString(),
                "phoneNumber" to userInfo.phoneNumber
            )
        } else {
            emptyMap()
        }
    }

}