package com.example.uzbexchangerate.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currencies")
data class ExchangeRate(
    @PrimaryKey
    val Ccy: String,
    val CcyNm_EN: String,
    val CcyNm_RU: String,
    val CcyNm_UZ: String,
    val CcyNm_UZC: String,
    val Code: String,
    val Date: String,
    val Diff: String,
    val Nominal: String,
    val Rate: String,
    val id: Int
)