package com.example.uzbexchangerate.network

import com.example.uzbexchangerate.models.ExchangeRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyService {

    @GET(" ")
    suspend fun getAllCurrency() : Response<List<ExchangeRate>>

    @GET("all/{date}/")
    suspend fun getDateAllCurrency(
        @Path("date") date: String
    ) : Response<List<ExchangeRate>>

    @GET("{ccy}/{date}/")
    suspend fun getSelectDateCurrency(
        @Path("ccy") ccy: String,
        @Path("date") date: String
    ) : Response<List<ExchangeRate>>

}