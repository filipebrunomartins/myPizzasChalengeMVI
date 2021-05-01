package com.example.mypizzasmvi.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvi.MviStateFragment
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.core.models.LoginResponse
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class LoginFragment : MviStateFragment<LoginState>() {

    override val viewModel: LoginViewModel by stateViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        button_login.setOnClickListener {
            viewModel.postLogin(user_text.text.toString(),password_text.text.toString())
        }
    }

    override fun render(state: LoginState) = when(state) {
        is LoginState.Error -> renderError(state.message)
        is LoginState.Success -> renderSuccess(state.loginResponse)
        is LoginState.Loading -> renderLoading()
    }

    private fun renderLoading(){

    }

    private fun renderError(message: String){
        message
    }

    private fun renderSuccess(loginResponse: LoginResponse){
        loginResponse
    }
}