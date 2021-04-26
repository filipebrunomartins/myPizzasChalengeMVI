package com.example.mvi

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData


abstract class MviStateFragment<S: Any>: Fragment() {

    abstract val viewModel: MviStateViewModel<S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel) { render(it) }
    }

    override fun onStart() {
        super.onStart()
        setViews()
        fetch()
    }

    private fun observe(viewModel: MviStateViewModel<S>, block: (S) -> Unit) {
        viewModel.state.asLiveData().observe(viewLifecycleOwner, MviEventObserver(block))
    }

    abstract fun render(state: S)

    open fun fetch(){}
    open fun setViews(){}
}