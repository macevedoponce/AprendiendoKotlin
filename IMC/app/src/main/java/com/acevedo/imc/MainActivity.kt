package com.acevedo.imc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    // creamos los tipos de variables a usar
    private var isMaleSelected:Boolean = true
    private var isFemaleSelected:Boolean = false
    private var currentWeight:Int = 70
    private var currentAge:Int = 20
    private var currentHeight:Int = 120

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvAge: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var btnCalculate: Button

    companion object{
        const val IMC_KEY = "IMC_RESULT"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initLListeners()
        initUI()
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
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
        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##") //decimal format
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }
        btnPlusWeight.setOnClickListener {
            currentWeight++
            setWeight()
        }
        btnSubtractWeight.setOnClickListener {
            currentWeight--
            setWeight()
        }
        btnPlusAge.setOnClickListener {
            currentAge++
            setAge()
        }
        btnSubtractAge.setOnClickListener {
            currentAge--
            setAge()
        }

        btnCalculate.setOnClickListener {
            val result = calculateIMC()
            navigateToResult(result)
        }

    }

    private fun navigateToResult(result: Double){
        val intent  = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY,result)
        startActivity(intent)
    }

    private fun calculateIMC():Double{
        val df = DecimalFormat("#.##")
        val imc = currentWeight / (currentHeight.toDouble() /100 * currentHeight.toDouble()/100)
        return df.format(imc).toDouble()

    }

    private fun setAge(){
        tvAge.text = currentAge.toString()
    }

    private fun setWeight(){
        tvWeight.text = currentWeight.toString()
    }

    private fun initComponents() { // conectamos las variables con la UI
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)
    }

    private fun changeGender(){
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
        //aqui hay un fallo de que cuando presionas un icono dos veces se cambia de igual manera
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