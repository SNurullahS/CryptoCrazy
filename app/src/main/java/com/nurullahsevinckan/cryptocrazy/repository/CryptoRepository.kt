package com.nurullahsevinckan.cryptocrazy.repository

import com.nurullahsevinckan.cryptocrazy.model.CryptoList
import com.nurullahsevinckan.cryptocrazy.services.CryptoAPI
import com.nurullahsevinckan.cryptocrazy.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api : CryptoAPI
) {

    suspend fun getCryptoList():Resource<List<CryptoList>>{
       val response = try {
           api.getCryptoList()
       }catch (e: Exception){
           return Resource.Error("There is an error in API call!")
       }
        return Resource.Success(response)
    }
}