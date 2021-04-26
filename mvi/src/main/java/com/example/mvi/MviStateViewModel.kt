package com.example.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


abstract class MviStateViewModel<S: Any>: ViewModel() {
    private val _state = MutableSharedFlow<MviEvent<S>>()
    val state get() = _state as SharedFlow<MviEvent<S>>

    protected suspend fun emit(value: S) {
        _state.emit(MviEvent(value))
    }
}