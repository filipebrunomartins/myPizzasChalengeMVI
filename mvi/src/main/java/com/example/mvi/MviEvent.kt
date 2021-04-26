package com.example.mvi

import androidx.lifecycle.Observer


open class MviEvent<out T>(private val _content: T) {

    var hasBeenHandled = false
        private set

    fun getIfNotHandled(): T? {
        return if(hasBeenHandled) null
        else _content.also { hasBeenHandled = true }
    }

}

class MviEventObserver<T>(private val block: (T) -> Unit): Observer<MviEvent<T>> {

    override fun onChanged(event: MviEvent<T>?) {
        event?.getIfNotHandled()?.let { block(it) }
    }

}