package com.example.mypizzasmvi.core.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mypizzasmvi.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.layout_dialog_error.*
import java.text.NumberFormat
import java.util.*

fun <FirstParameterType, SecondParameterType, ReturnType> combineLatest(
    first: LiveData<FirstParameterType>,
    second: LiveData<SecondParameterType>,
    combine: (FirstParameterType?, SecondParameterType?) -> ReturnType?
): MediatorLiveData<ReturnType> {
    return MediatorLiveData<ReturnType>().apply {
        var latestFirst: FirstParameterType? = null
        var latestSecond: SecondParameterType? = null

        fun updateValue() {
            value = combine(latestFirst, latestSecond)
        }

        addSource(first) {
            latestFirst = it
            updateValue()
        }
        addSource(second) {
            latestSecond = it
            updateValue()
        }
    }
}

fun Fragment.showMessageError(
    message: String,
    title: String = "Atenção",
    textButton: String = "Fechar",
    onClickButton: () -> Unit = {},
    onClose: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    openBottomDialog(
        requireContext(),
        dialogData = DialogModel(title, message, textButton),
        onClickButton = onClickButton,
        onClose = onClose, onFinish = onFinish)
}

fun openBottomDialog(
    context: Context,
    dialogData: DialogModel,
    drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_error_black),
    onClickButton: () -> Unit = {},
    onClose: () -> Unit = {},
    onFinish: () -> Unit = {},
): Dialog {
    val dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
    dialog.setContentView(R.layout.layout_dialog_error)
    dialog.image_view_dialog_error.setImageDrawable(drawable)
    dialog.show()
    dialog.setCanceledOnTouchOutside(false)
    dialog.setOnDismissListener { onFinish() }
    dialog.text_view_title_dialog_error.text = dialogData.title
    dialog.text_view_sub_title_dialog_error.text = dialogData.description
    dialog.button_dialog_error.text = dialogData.textButton
    dialog.button_dialog_error.visibility = View.VISIBLE

    dialog.image_view_close_dialog_error.setOnSingleClickListener {
        onClose()
        dialog.dismiss()
    }
    dialog.button_dialog_error.setOnSingleClickListener {
        onClickButton()
        dialog.dismiss()
    }
    return dialog
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

class OnSingleClickListener : View.OnClickListener {
    private val onClickListener: View.OnClickListener

    constructor(listener: View.OnClickListener) {
        onClickListener = listener
    }

    constructor(listener: (View) -> Unit) {
        onClickListener = View.OnClickListener { listener.invoke(it) }
    }

    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            onClickListener.onClick(v)
        }
    }

    companion object {
        private const val DELAY_MILLIS = 475L

        private var previousClickTimeMillis = 0L
    }
}

fun Fragment.lockScreen(lockScreen: Boolean) {
    val window: Window = this.requireActivity().window
    if (lockScreen) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }
}

fun Double.toMoneyFormat(): String {
    val value =
        NumberFormat.getCurrencyInstance(MONETARY_DEFAULT_LOCALE).format(this).split('$')[1].trim()
    return "R$ $value"
}

private val MONETARY_DEFAULT_LOCALE = Locale("pt", "BR")

data class DialogModel(
    val title: String = "Atenção!",
    val description: String,
    val textButton: String? = "Dúvidas? Fale conosco"
)