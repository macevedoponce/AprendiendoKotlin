package com.acevedo.superheroapp_api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/3019786624831420/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superHeroName: String):Response<SuperHeroDataResponse> // devuelve un listado de heroes

    @GET("/api/3019786624831420/{id}")
    suspend fun getSuperHeroDetail(@Path("id") superHeroId:String):Response<SuperHeroDetailResponse>

}