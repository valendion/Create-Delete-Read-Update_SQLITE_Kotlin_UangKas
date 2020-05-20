package com.example.uangkas

import android.app.Application
import com.example.uangkas.data.db.DatabaseHelper

class App: Application() {

    companion object{
        var db : DatabaseHelper? = null
    }

    override fun onCreate() {
        //agar dapat bisa diran tanpa masuk activity, karna class ketika mau di eksekusi masuk dlu aktivity
        db = DatabaseHelper(this)
        super.onCreate()
    }

}