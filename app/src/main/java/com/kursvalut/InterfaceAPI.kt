package com.kursvalut

import retrofit2.Call
import retrofit2.http.GET


//Создаем интерфейс для Ретрофита
interface InterfaceAPI {


    @GET("daily_json.js")
    fun getKurs(
    ): Call<ValuteResponse>

}
