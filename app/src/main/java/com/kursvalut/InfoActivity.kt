package com.kursvalut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.TextView


class InfoActivity : AppCompatActivity() {
    //переменная, в которой будут хранится данные о названии валюты
    var currency: String = ""

    //переменная, в которой будут хранится данные о количестве единиц валюты( о номинале)
    var nominal: Int = 0

    //переменная, в которой будут хранится данные о стоимости номинала валюты в рублях
    var value: Double = 0.0

    //переменная, в которой будут хранится данные о к3-з значной абривиатуре валюты
    var charCode: String = ""

    //переменная, в которой будут хранится данные о количестве рублей для обмена
    var exchange: Int = 0

    //объявление перепенных для элементов Активити(вьюшек)
    lateinit var textViewExchange: TextView
    lateinit var textViewCurrency: TextView
    lateinit var textViewNominal: TextView
    lateinit var textViewValueAfterExchange: TextView
    lateinit var editExchange: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Currency)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        textViewExchange = findViewById(R.id.textViewExchange)
        textViewCurrency = findViewById(R.id.textViewCurrency)
        textViewNominal = findViewById(R.id.textViewNominal)
        editExchange = findViewById(R.id.editExchange)
        textViewValueAfterExchange = findViewById(R.id.textViewValueAfterExchange)

        //получаем данные из главной активности и присваиваем их соответствующим переменным
        currency = intent.getStringExtra("currency").toString()
        nominal = intent.getStringExtra("nominal")!!.toInt()
        value = intent.getStringExtra("value")!!.toDouble()
        charCode = intent.getStringExtra("charCode").toString()

        //присваеваем новые значения текстовым View
        textViewCurrency.setText("Сurrency: $currency")
        textViewValueAfterExchange.setText("Value of currency after exchange: 0.00 " + charCode)
    }

    // объявление функции нажатия на кнопку по возврату на основную активность
    fun onClickReturn(view: View) {
        this.finish()
    }

    // объявление функции нажатия на кнопку по подсчету обмена рублей в выбранную валюту
    fun onClickExchange(view: View) {

        exchange = editExchange.text.toString().toInt()
        //переменная, в которой будут хранится данные о результате обмена рублей на указанную валюту
        var result = String.format("%.2f", exchange / (value / nominal))
        textViewValueAfterExchange.text =
            "Value of currency after exchange: " + result + " " + charCode
    }
}




