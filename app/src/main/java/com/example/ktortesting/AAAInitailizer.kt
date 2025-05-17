package com.example.ktortesting

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.InitializationProvider
import androidx.startup.Initializer
import com.example.ktortesting.struct.School

class AAAInitailizer : Initializer<School> {

    override fun create(context: Context): School {
        val kkman = AppInitializer.getInstance(context)
            .initializeComponent(AAAInitailizer::class.java)
        return School("", "")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}