package com.example.mypizzasmvi.ui.home.pizzaList

import androidx.lifecycle.viewModelScope
import com.example.mvi.MviStateViewModel
import com.example.mypizzasmvi.core.models.PizzaModel
import com.example.mypizzasmvi.core.retrofit.RetrofitRequest
import com.example.mypizzasmvi.core.services.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PizzaListViewModel (
    private val service: Services
) : MviStateViewModel<PizzaListState>() {

    //TODO tratar para salvar lista e não refazer requisição

    fun getListPizza() = viewModelScope.launch(Dispatchers.Default) {
        emit(PizzaListState.Loading)
        val response = doRequest()
        emit(reduce(response))
    }

    private suspend fun doRequest() = withContext(Dispatchers.IO) {
        RetrofitRequest.doRetrofitRequest(
            call = { service.getListPizza() })
    }

    private fun reduce(response: RetrofitRequest.RetrofitTreatedRequest<List<PizzaModel>>) = when {
        response.hasError -> PizzaListState.Error(response.message ?: "Erro inesperado")
        else -> handleSuccess(response.response!!)
    }

    private fun handleSuccess(response: List<PizzaModel>): PizzaListState {
        return PizzaListState.Success(response)
    }
}

sealed class PizzaListState {
    class Error(val message: String): PizzaListState()
    object Loading : PizzaListState()
    class Success(val listPizza: List<PizzaModel>): PizzaListState()
}
