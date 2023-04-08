package com.acevedo.superheroapp_api

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperHeroItemResponse>
) //siempre tiene que ser el nombre que verifica que la conexión es success en el API

data class SuperHeroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
//    @SerializedName("powerstats") val name: String,
//    @SerializedName("biography") val name: String,
//    @SerializedName("appearance") val name: String,
//    @SerializedName("work") val name: String,
//    @SerializedName("connections") val name: String,
    @SerializedName("image") val superheroImage:SuperHeroImageResponse
)

data class SuperHeroImageResponse(@SerializedName("url") val url:String)
