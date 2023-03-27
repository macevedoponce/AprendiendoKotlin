package com.acevedo.firstapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnFfirstApp = findViewById<Button>(R.id.btnFirst)
        val btnAppImc = findViewById<Button>(R.id.btnIMC)

        btnFfirstApp.setOnClickListener { navigateToSaludar() }
        btnAppImc.setOnClickListener { imc() }
    }
    private fun navigateToSaludar(){
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }

    private fun imc(){
        val i = Intent(this,ImcActivity::class.java)
        startActivity(i)
    }
}