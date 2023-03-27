package com.acevedo.firstapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<Button>(R.id.btnEntrar)
        val edtTexto = findViewById<EditText>(R.id.edtTexto)

        btnStart.setOnClickListener {
            val texto = edtTexto.text.toString()
            if(texto.isNotEmpty()){
                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("nombre",texto)
                startActivity(intent)
            }
        }
    }
}