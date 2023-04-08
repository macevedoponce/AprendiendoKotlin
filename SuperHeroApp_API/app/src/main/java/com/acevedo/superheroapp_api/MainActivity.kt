package com.acevedo.superheroapp_api

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.acevedo.superheroapp_api.DetailSuperHeroActivity.Companion.EXTRA_ID
import com.acevedo.superheroapp_api.databinding.ActivityMainBinding
import com.acevedo.superheroapp_api.settings.SettingsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding // sirve para crear variable, iniciar la varibale, buscar la variable con el xml, ahora puedes acceder rápido
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hasta qui se prepara el binding

        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{//tener en cuenta el tipo de searchview creado en el layout
            override fun onQueryTextSubmit(query: String?): Boolean { //esto se llama automaticamente cuando pulsamos en buscar
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false

        })

        //iniciado adapter
        adapter = SuperHeroAdapter{navigateToDetail(it)} // esto es igual a poner: superheroID ->navigateToDetail(superHeroId)
        binding.rvSuperHero.setHasFixedSize(true)
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHero.adapter = adapter
        binding.fabSettings.setOnClickListener { navigateToSettings() }

    }

    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun searchByName(query: String) {
        //mostrar progress bar
        binding.progressBar.isVisible = true

        //corrutinas
        CoroutineScope(Dispatchers.IO).launch{
            //todo se hace en un hilo secundario
            val myResponse = retrofit.create(ApiService::class.java).getSuperHeroes(query)
            if(myResponse.isSuccessful){
                Log.i("macevedo","Funciona")
                val response: SuperHeroDataResponse? = myResponse.body()
                if(response != null){
                    Log.i("macevedo",response.toString())
                    runOnUiThread{ //para que se ejecute en el hilo principal, ya que no se puede hacer en un hilo secundario
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false
                     }
                }

            }else{
                Log.i("macevedo","No Funciona")
            }
        }
    }

    private fun getRetrofit():Retrofit{
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    private fun navigateToDetail(id:String){
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(EXTRA_ID,id)
        startActivity(intent)
    }
}