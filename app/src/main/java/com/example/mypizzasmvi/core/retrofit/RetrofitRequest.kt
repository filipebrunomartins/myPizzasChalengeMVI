package com.example.mypizzasmvi.core.retrofit

import com.example.mypizzasmvi.core.connection.ConnectionChecker
import com.example.mypizzasmvi.core.models.ErrorResponse
import com.example.mypizzasmvi.core.utils.Constants.Companion.CONNECTION_ERROR
import com.example.mypizzasmvi.core.utils.Constants.Companion.UNEXPECTED_ERROR
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.net.UnknownHostException

typealias SuspendCall<T> = suspend () -> Response<T>?

object RetrofitRequest : KoinComponent {

    private val connectionChecker: ConnectionChecker by inject()
    private val type: Type = object : TypeToken<ErrorResponse>() {}.type

    suspend fun <T> doRetrofitRequest(
        call: SuspendCall<T>
    ): RetrofitTreatedRequest<T> {
        return try {
            if (connectionChecker.isConnected().not())
                return RetrofitTreatedRequest(hasError = true, message = CONNECTION_ERROR)
            val response = withContext(Dispatchers.IO) { call.invoke() }
            when (response?.code()) {
                in 200..299 -> RetrofitTreatedRequest(response = response?.body(), isSuccess = true)
                else -> {
                    val errorResponse: ErrorResponse? =
                        Gson().fromJson(response?.errorBody()!!.charStream(), type)
                    RetrofitTreatedRequest(
                        isSessionExpired = response.code() == 401,
                        hasError = true,
                        message = errorResponse?.message ?: UNEXPECTED_ERROR,
                        status = response.code(),
                        code = errorResponse?.code ?: "400",
                    )
                }
            }
        } catch (e: UnknownHostException) {
            RetrofitTreatedRequest(hasError = true, message = UNEXPECTED_ERROR)
        } catch (e: IOException) {
            RetrofitTreatedRequest(hasError = true, message = UNEXPECTED_ERROR)
        } catch (e: Exception) {
            RetrofitTreatedRequest(hasError = true, message = UNEXPECTED_ERROR)
        }
    }


    data class RetrofitTreatedRequest<T>(
        val response: T? = null,
        val isSessionExpired: Boolean = false,
        val hasError: Boolean = false,
        val isSuccess: Boolean = false,
        val message: String? = null,
        val status: Int? = null,
        val code: String = "",
    )

}

//TODO testar depois
suspend fun <T> doRequest(call: SuspendCall<T>) = withContext(Dispatchers.IO) {
    RetrofitRequest.doRetrofitRequest(call)
}