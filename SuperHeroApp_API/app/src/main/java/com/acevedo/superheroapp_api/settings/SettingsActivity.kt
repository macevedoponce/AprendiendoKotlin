package com.acevedo.superheroapp_api.settings

import android.content.Context
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.acevedo.superheroapp_api.R
import com.acevedo.superheroapp_api.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="settings") //crear una unica instancia de la bd


class SettingsActivity : AppCompatActivity() {


    companion object{
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_BLUETOOTH = "key_bluetooth"
        const val KEY_VIBRATION = "key_vibration"
        const val KEY_DARK_MODE = "key_dark_mode"
    }
    private lateinit var binding: ActivitySettingsBinding
    private var firstTime:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO)
            .launch {
                getSettings().filter {firstTime}.collect{settingsModel->
                    if(settingsModel != null){
                        runOnUiThread{
                            binding.switchVibration.isChecked = settingsModel.vibration
                            binding.switchBluetooth.isChecked = settingsModel.bluetooth
                            binding.switchDarkMode.isChecked = settingsModel.darkMode
                            binding.rsVolume.setValues(settingsModel.volume.toFloat())
                            firstTime = !firstTime
                        }

                    }

                }

            }
            //datos SettingsModel()
        initUI()


    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener { _, value, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
        }

        binding.switchBluetooth.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_BLUETOOTH,value)
            }
        }

        binding.switchVibration.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_VIBRATION,value)
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            if(value){
                enableDarkMode()
            }else{
                disableDarkMode()
            }
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_DARK_MODE,value)
            }
        }
    }

    private suspend fun saveVolume(value:Int){ //ejecutar en un corrutina: otro hilo ya que la principal pinta la interface
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(VOLUME_LVL)] = value
        }
    }

    private suspend fun saveOptions(key:String, value:Boolean){
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsModel?> { //flujo que devuelve un objeto
        return dataStore.data.map { preferences ->
            SettingsModel(
                volume =  preferences[intPreferencesKey(VOLUME_LVL)] ?: 50,
                bluetooth =  preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                vibration =  preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: false,
                darkMode =  preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: true

            )


        }
    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}