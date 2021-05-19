package com.example.mypizzasmvi.ui.login

import android.content.Intent
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
import com.example.mypizzasmvi.databinding.FragmentLoginBinding
import com.example.mypizzasmvi.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : MviStateFragment<LoginState>() {

    private lateinit var binding: FragmentLoginBinding
    override val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.buttonProgress.setOnSingleClickListener {
            viewModel.postLogin(binding.userText.text.toString(),binding.passwordText.text.toString())
        }
    }

    private fun setupObservers(){

        binding.userText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                viewModel.userLiveData.postValue(true)
            }else viewModel.userLiveData.postValue(false)
        }
        binding.passwordText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                viewModel.passwordLiveData.postValue(true)
            }else viewModel.passwordLiveData.postValue(false)
        }

        viewModel.buttonLiveData.observe(viewLifecycleOwner, Observer { enable ->
            binding.buttonProgress.isEnabled = enable
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
        //TODO tratar token
        loginResponse
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showLoading(){
        lockScreen(true)
        binding.buttonProgress.isEnabled = false
        binding.textButton.visibility = View.GONE
        binding.progressButton.visibility = View.VISIBLE
    }

    private fun hidingLoading(){
        lockScreen(false)
        binding.buttonProgress.isEnabled = true
        binding.textButton.visibility = View.VISIBLE
        binding.progressButton.visibility = View.GONE
    }
}