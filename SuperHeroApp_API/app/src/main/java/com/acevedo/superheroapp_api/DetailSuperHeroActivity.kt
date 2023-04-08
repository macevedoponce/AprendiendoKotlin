package com.acevedo.superheroapp_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.view.isVisible
import com.acevedo.superheroapp_api.databinding.ActivityDetailSuperHeroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSuperHeroBinding

    //la finalidad de const es no confundirnos al pasar datos con intent
    companion object{
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_super_hero)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty() //si el valor es nulo guarda como vacio
        getSuperHeroInformation(id)
    }

    private fun getSuperHeroInformation(id: String) {

        //corrutinas -> realizar la busqueda en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch{
           val superheroDetail= getRetrofit().create(ApiService::class.java).getSuperHeroDetail(id)

            if(superheroDetail.body() != null){
                runOnUiThread { createUI(superheroDetail.body()!!) } //seguro que no es nulo

            }
        }
    }

    private fun createUI(superhero: SuperHeroDetailResponse) {
        Picasso.get().load(superhero.image.url).into(binding.ivSuperhero)
        binding.tvSuperheroName.text = superhero.name
        prepareStats(superhero.powerstats)
        binding.tvSuperheroRealName.text = superhero.biography.fullName
        binding.tvPublisher.text = superhero.biography.publisher
    }

    private fun prepareStats(powerstats: PowerStatsResponse) {
        updateHeight(binding.viewCombat,powerstats.combat)
        updateHeight(binding.viewDurability,powerstats.durability)
        updateHeight(binding.viewSpeed,powerstats.speed)
        updateHeight(binding.viewIntelligence,powerstats.intelligence)
        updateHeight(binding.viewStrength,powerstats.strength)
        updateHeight(binding.viewPower,powerstats.power)

    }

    private fun updateHeight(view:View, stat:String){
        val params = view.layoutParams
        params.height = pxToDp(stat.toFloat())
        view.layoutParams = params
    }

    private fun pxToDp(px:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
}