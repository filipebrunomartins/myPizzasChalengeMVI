package com.example.mypizzasmvi.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.mvi.MviStateFragment
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.core.models.LoginResponse
import com.example.mypizzasmvi.core.utils.lockScreen
import com.example.mypizzasmvi.core.utils.setOnSingleClickListener
import com.example.mypizzasmvi.core.utils.showMessageError
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
        setupObservers()
    }

    private fun setupViews() {
        button_progress.setOnSingleClickListener {
            viewModel.postLogin(user_text.text.toString(),password_text.text.toString())
        }
    }

    private fun setupObservers(){

        user_text.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                viewModel.userLiveData.postValue(true)
            }else viewModel.userLiveData.postValue(false)
        }
        password_text.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                viewModel.passwordLiveData.postValue(true)
            }else viewModel.passwordLiveData.postValue(false)
        }

        viewModel.buttonLiveData.observe(viewLifecycleOwner, Observer { enable ->
            button_progress.isEnabled = enable
        })

    }

    override fun render(state: LoginState) = when(state) {
        is LoginState.Error -> renderError(state.message)
        is LoginState.Success -> renderSuccess(state.loginResponse)
        is LoginState.Loading -> showLoading()
    }

    private fun renderError(message: String){
        hidingLoading()
        showMessageError(message, getString(R.string.attention_text))
    }

    private fun renderSuccess(loginResponse: LoginResponse){
        hidingLoading()
        //TODO startActivity Home
        loginResponse
    }

    private fun showLoading(){
        lockScreen(true)
        button_progress.isEnabled = false
        text_button.visibility = View.GONE
        progress_button.visibility = View.VISIBLE
    }

    private fun hidingLoading(){
        lockScreen(false)
        button_progress.isEnabled = true
        text_button.visibility = View.VISIBLE
        progress_button.visibility = View.GONE
    }
}