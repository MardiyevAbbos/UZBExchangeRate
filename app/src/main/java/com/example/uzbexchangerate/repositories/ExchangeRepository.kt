package com.example.uzbexchangerate.repositories

import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.network.CurrencyService
import com.example.uzbexchangerate.utils.NetworkResource
import com.example.uzbexchangerate.utils.UiText
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val currencyService: CurrencyService
) {

    suspend fun getSelectDateCurrency(ccy: String, date: String) : NetworkResource<List<ExchangeRate>>{
        return try {
            val response = currencyService.getSelectDateCurrency(ccy, date)
            if (response.isSuccessful){
                NetworkResource.Success(response.body())
            }else{
                NetworkResource.Error(UiText.StaticString())
            }
        }catch (e: HttpException){
            NetworkResource.Error(UiText.StaticString())
        }catch (e: IOException){
            NetworkResource.Error(UiText.StaticString())
        }
    }

}