package com.nurullahsevinckan.cryptocrazy.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurullahsevinckan.cryptocrazy.model.CryptoList
import com.nurullahsevinckan.cryptocrazy.repository.CryptoRepository
import com.nurullahsevinckan.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository : CryptoRepository
): ViewModel(){


    var cryptoList = mutableStateOf<List<CryptoList>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    fun loadCryptos(){
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getCryptoList()
            when(result){
                is Resource.Success -> {
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoList ->
                        CryptoList(cryptoList.currency,cryptoList.price)
                    }
                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }
                is Resource.Error -> {errorMessage.value = result.message!!}
                is Resource.Loading -> TODO()
            }
        }
    }
}