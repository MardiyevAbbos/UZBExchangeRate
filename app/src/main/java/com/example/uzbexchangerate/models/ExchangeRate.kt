package com.example.uzbexchangerate.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serializable


@Entity(tableName = "currencies")
data class ExchangeRate(
    @PrimaryKey
    @Json(name = "Ccy")
    val Ccy: String,
    @Json(name = "CcyNm_EN")
    val CcyNm_EN: String,
    @Json(name = "CcyNm_RU")
    val CcyNm_RU: String,
    @Json(name = "CcyNm_UZ")
    val CcyNm_UZ: String,
    @Json(name = "CcyNm_UZC")
    val CcyNm_UZC: String,
    @Json(name = "Code")
    val Code: String,
    @Json(name = "Date")
    val Date: String,
    @Json(name = "Diff")
    val Diff: String,
    @Json(name = "Nominal")
    val Nominal: String,
    @Json(name = "Rate")
    val Rate: String,
    @Json(name = "id")
    val id: Int
) : java.io.Serializable