package com.example.mypizzasmvi.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvi.MviStateViewModel
import com.example.mypizzasmvi.core.models.LoginModel
import com.example.mypizzasmvi.core.models.LoginResponse
import com.example.mypizzasmvi.core.retrofit.RetrofitRequest
import com.example.mypizzasmvi.core.services.Services
import com.example.mypizzasmvi.core.utils.combineLatest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(
    private val service: Services
) : MviStateViewModel<LoginState>() {

    val userLiveData= MutableLiveData(false)
    val passwordLiveData = MutableLiveData(false)

    val buttonLiveData: LiveData<Boolean> = combineLatest(
            userLiveData,
            passwordLiveData
    ) isValid@{ user, password ->
        return@isValid user == true
                && password == true
    }

    fun postLogin(user : String, password : String) = viewModelScope.launch(Dispatchers.Default) {
        emit(LoginState.Loading)
        val response = doRequest(LoginModel(user,password))
        emit(reduce(response))
    }

    private suspend fun doRequest(loginModel: LoginModel) = withContext(Dispatchers.IO) {
        RetrofitRequest.doRetrofitRequest(
            call = { service.login(loginModel) })
    }

    private fun reduce(response: RetrofitRequest.RetrofitTreatedRequest<LoginResponse>) = when {
        response.hasError -> LoginState.Error(response.message ?: "Erro inesperado")
        else -> handleSuccess(response.response!!)
    }

    private fun handleSuccess(response: LoginResponse): LoginState {
        return LoginState.Success(response)
    }
}

sealed class LoginState {
    class Error(val message: String): LoginState()
    object Loading : LoginState()
    class Success(val loginResponse: LoginResponse): LoginState()
}
