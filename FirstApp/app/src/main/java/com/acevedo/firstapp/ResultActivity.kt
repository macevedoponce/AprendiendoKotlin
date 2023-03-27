package com.acevedo.firstapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        val exNombre:String = intent.extras?.getString("nombre").orEmpty()
        txtResult.text = "Hola $exNombre"
    }
}