package com.nurullahsevinckan.cryptocrazy.services

import com.nurullahsevinckan.cryptocrazy.model.CryptoList
import retrofit2.http.GET
interface CryptoAPI {

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getCryptoList() : List<CryptoList>
}