package com.example.intuitivecats

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

//import android.app.Application
//import android.content.Context
//import androidx.test.runner.AndroidJUnitRunner
//import dagger.hilt.android.testing.HiltTestApplication
//
//class TestRunner : AndroidJUnitRunner() {
//    override fun newApplication(
//        cl: ClassLoader,
//        appName: String,
//        context: Context
//    ): Application {
//        return super.newApplication(
//            cl, HiltTestApplication::class.java.name, context
//        )
//    }
//}

class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}