package com.kursvalut

import com.google.gson.annotations.SerializedName


 data class ValuteResponse(
@SerializedName("Date")
val date: String = "",
@SerializedName("PreviousDate")
val previousDate: String = "",
@SerializedName("PreviousURL")
val previousURL: String = "",
@SerializedName("Timestamp")
val timestamp: String = "",
@SerializedName("Valute")
val valutes: Map<String, Valute> = emptyMap(),
)

data class Valute(
 @SerializedName("CharCode")
 val charCode: String = "",
 @SerializedName("ID")
 val iD: String = "",
 @SerializedName("Name")
 val name: String = "",
 @SerializedName("Nominal")
 val nominal: Int = 0,
 @SerializedName("NumCode")
 val numCode: String = "",
 @SerializedName("Previous")
 val previous: Double = 0.0,
 @SerializedName("Value")
 val value: Double = 0.0,
)