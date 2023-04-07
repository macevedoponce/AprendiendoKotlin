package com.acevedo.imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.acevedo.imc.MainActivity.Companion.IMC_KEY

class ResultIMCActivity : AppCompatActivity() {
    private lateinit var tvResult:TextView
    private lateinit var tvDescription:TextView
    private lateinit var tvIMC:TextView
    private lateinit var btnRecalculate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)
        val result = intent.extras?.getDouble(IMC_KEY) ?: -1.0 //si tiene algun extra con la clave IMC_KEY que es una constante en mainActivity
        initcomponent()
        initUI(result)
        initListeners()
    }

    private fun initcomponent() {
        tvIMC = findViewById(R.id.tvIMC)
        tvResult = findViewById(R.id.tvResult)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)
    }

    private fun initListeners(){
        btnRecalculate.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initUI(result: Double) {
        tvIMC.text = result.toString()
        when(result){
            in 0.00..18.50 ->{ //bajo peso
                tvResult.text = getString(R.string.title_bajo_peso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_bajo))
                tvDescription.text = getString(R.string.description_bajo_peso)
            }
            in 18.51..24.99 ->{// peso normal
                tvResult.text = getString(R.string.title_peso_normal)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_normal))
                tvDescription.text = getString(R.string.description_peso_normal)
            }
            in 25.00..29.99 ->{// sobrepeso
                tvResult.text = getString(R.string.title_sobre_peso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.sobre_peso))
                tvDescription.text = getString(R.string.description_sobre_peso)
            }
            in 30.00..99.00 ->{// obesidad
                tvResult.text = getString(R.string.title_obesidad)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
                tvDescription.text = getString(R.string.description_obesidad)
            }
            else ->{//error
                tvIMC.text = getString(R.string.error)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
                tvResult.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
            }
        }
    }
}