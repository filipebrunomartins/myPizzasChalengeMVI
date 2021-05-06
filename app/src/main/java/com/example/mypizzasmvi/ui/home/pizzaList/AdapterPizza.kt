package com.example.mypizzasmvi.ui.home.pizzaList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.core.models.PizzaModel
import com.example.mypizzasmvi.core.utils.setOnSingleClickListener
import com.example.mypizzasmvi.core.utils.toMoneyFormat
import kotlinx.android.synthetic.main.item_list_pizza.view.*

class AdapterPixWithBank (
    private val ctx: Context,
    private var fullDataSet: List<PizzaModel> = listOf(),
    private val onClick: (PizzaModel) -> Unit
) : RecyclerView.Adapter<AdapterPixWithBank.PixWithBankViewHolder>(){

    private var query: String? = null
    private var filteredDataSet: List<PizzaModel> = listOf()

    fun filter(query: String?) {
        this.query = query
        filteredDataSet = listOf()

        filteredDataSet = if (!query.isNullOrBlank()) {
            fullDataSet.filter { pizza ->
                pizza.name.toLowerCase().contains(query.toLowerCase())
            }
        } else fullDataSet

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixWithBankViewHolder {
        return PixWithBankViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_list_pizza, parent, false),onClick)
    }

    override fun getItemCount() = filteredDataSet.size


    override fun onBindViewHolder(holder: AdapterPixWithBank.PixWithBankViewHolder, position: Int) {
        holder.bind(filteredDataSet[position])
    }

    inner class PixWithBankViewHolder(private val view : View, val onClick: (PizzaModel) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(pizza: PizzaModel) {
            view.txtNamePizza.text = pizza.name
            view.txtPriceValue.text = pizza.priceP.toMoneyFormat()
            view.rattingBarPizza.rating = pizza.rating
            view.item_list_pizza_card.setOnSingleClickListener {
                onClick(pizza)
            }
        }
    }
}