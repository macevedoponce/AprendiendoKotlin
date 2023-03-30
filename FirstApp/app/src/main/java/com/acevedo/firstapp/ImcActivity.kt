package com.acevedo.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import androidx.cardview.widget.CardView

class ImcActivity : AppCompatActivity() {
    // creamos los tipos de varialbes a usar
    private var isMaleSelected:Boolean = true
    private var isFemaleSelected:Boolean = false
    private lateinit var viewMale:CardView
    private lateinit var viewFemale:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        initComponents()
        initLListeners()
        initUI()
    }

    private fun initUI() {
        setGenderColor()
    }

    private fun initLListeners() {
        viewMale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            changeGender()
            setGenderColor()
        }
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
    }

    private fun changeGender(){
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
        //aqui hay un fallo de que cuendo presionas un icono dos veces se cambia de igual manera
    }

    private fun setGenderColor(){

        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))

    }

    private fun getBackgroundColor(isSelectedComponent:Boolean) : Int{ //funcion que recibe un boolean y devuelve un int

        val colorReferece = if(isSelectedComponent){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }

        return ContextCompat.getColor(this,colorReferece)
    }
}