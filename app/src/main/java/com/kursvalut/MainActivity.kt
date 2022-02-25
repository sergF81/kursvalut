package com.kursvalut


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    //создаем массивы, в котором будут храниться данные о названии текущей валюты
    var currencyArray: MutableList<String> = mutableListOf()

    //создаем массивы, в котором будут храниться данные о номинальном количестве текущей валюты
    var nominalArray: MutableList<String> = mutableListOf()

    //создаем массивы, в котором будут храниться данные о значении текущей валюты
    var currencyValueArray: MutableList<String> = mutableListOf()

    //создаем массивы, в котором будут храниться данные об абривиатуре валюты
    var charCodeArray: MutableList<String> = mutableListOf()

    // переменная для хранения ссылки к API серверу
    val baseUrl = "https://www.cbr-xml-daily.ru/"

    //инициализация переменных для элементов Активити
    lateinit var buttonConnect: Button
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        //устанавливаем другую тему
        setTheme(R.style.Currency)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonConnect = findViewById(R.id.buttonConnect)
        currencyArray.add("No data for currency...")

        recyclerViewFunction()
        //создаем дополнительный поток для запуска счетчика, который запускает обновление курса валют каждые 15 секунд
        val v = Thread {
            var i: Int = 0
            while (i < 16) {
                i++
                Thread.sleep(1000)
                if (i == 15) {
                    i = 0
                    currencyRetrofit()
                }
            }
        }
        //запускаем вышеописанный поток
        v.start()
    }

    //
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //описываем саму функцию Retrofit для получения данных с сайта, которые находятся там в формате JSON
    fun currencyRetrofit() {

        var client: InterfaceAPI = retrofit.create(InterfaceAPI::class.java)
        val call: Call<ValuteResponse> = client.getKurs()
        call.enqueue(object : Callback<ValuteResponse> {

            override fun onResponse(
                call: Call<ValuteResponse>,
                response: Response<ValuteResponse>
            ) {
                if (!response.isSuccessful) {
                    println("code: " + response.code())
                    return
                }


                val kurs = response.body()?.valutes
                currencyArray.clear()
                kurs?.forEach {
                    currencyArray.add(it.value.nominal.toString() + " " + it.value.name + ": " + it.value.value.toString())
                    nominalArray.add(it.value.nominal.toString())
                    currencyValueArray.add(it.value.value.toString())
                    charCodeArray.add(it.value.charCode)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ValuteResponse>, t: Throwable) {
                println(t)
            }
        }
        )
    }

    //объявляем функцию обработки нажатия на кнопку buttonSearch - поиск для введеного логина на сервере github
    fun onClickConnect(view: View) {
        currencyRetrofit()
        recyclerViewFunction()
    }


    //объявляем функцию для вывода информации о курсах валют(содержимое массива currencyArray) и обработка нажатия на элемент списка
    fun recyclerViewFunction() {

        recyclerView = findViewById(R.id.listCurrencyView)
        var adapter =
            RecyclerViewAdapter(this, currencyArray, object : RecyclerViewAdapter.MyListener {

                override fun MyClick(currencyArray: MutableList<String>, position: Int) {

                    if (currencyArray[0].equals("No data for currency...")) {
                        return
                    } else {
                        val intent = Intent(this@MainActivity, InfoActivity::class.java)
                        //передача данных в другую активность
                        intent.putExtra("currency", currencyArray[position])
                        intent.putExtra("nominal", nominalArray[position])
                        intent.putExtra("value", currencyValueArray[position])
                        intent.putExtra("charCode", charCodeArray[position])

                        //запуск новой активности
                        startActivity(intent)
                    }
                }
            })
        recyclerView.adapter = adapter;
    }
}

