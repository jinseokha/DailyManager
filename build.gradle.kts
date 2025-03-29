// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.firebase.crashlytics)
    }
}

plugins {

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin) apply false
    alias(libs.plugins.google.devtools) apply false
    alias(libs.plugins.google.dagger.hilt) apply false

}
