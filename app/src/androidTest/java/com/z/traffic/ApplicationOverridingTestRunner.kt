package com.z.traffic

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class ApplicationOverridingTestRunner : AndroidJUnitRunner(){
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, InstrumentationTestApplication::class.java.name, context)
    }
}