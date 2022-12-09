package com.example.uzbexchangerate.repositories

import android.util.Log
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.network.CurrencyService
import com.example.uzbexchangerate.room.dao.CurrencyDao
import com.example.uzbexchangerate.utils.NetworkResource
import com.example.uzbexchangerate.utils.UiText
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
){

    suspend fun getAllCurrency(): NetworkResource<List<ExchangeRate>> {
        return try {
            val response = currencyService.getAllCurrency()
            if (response.isSuccessful){
                NetworkResource.Success(response.body())
            }else {
                NetworkResource.Error(UiText.StaticString())
            }
        }catch (e: HttpException){
            NetworkResource.Error(UiText.StaticString())
        }catch (e: IOException){
            NetworkResource.Error(UiText.StaticString())
        }
    }

    suspend fun getDateAllCurrency(date: String): NetworkResource<List<ExchangeRate>> {
        return try {
            val response = currencyService.getDateAllCurrency(date = date)
            if (response.isSuccessful){
                NetworkResource.Success(response.body())
            }else {
                NetworkResource.Error(UiText.StaticString())
            }
        }catch (e: HttpException){
            NetworkResource.Error(UiText.StaticString())
        }catch (e: IOException){
            NetworkResource.Error(UiText.StaticString())
        }
    }

    suspend fun saveCurrencyLocal(currencyEntity: ExchangeRate) = currencyDao.saveCurrency(currencyEntity)

    suspend fun getAllCurrencyLocal() = currencyDao.getAllCurrency()

    suspend fun deleteCurrencyByIdLocal(ccy: String) = currencyDao.deleteCurrencyById(ccy)

    suspend fun updateCurrencyLocal(currency: ExchangeRate) = currencyDao.updateCurrency(currency)

    suspend fun updateFieldsCurrencyLocal(rate: String, diff: String, date: String, ccy: String) = currencyDao.updateCurrencyFields(
       rate = rate, diff = diff, date = date, ccy = ccy
    )

}