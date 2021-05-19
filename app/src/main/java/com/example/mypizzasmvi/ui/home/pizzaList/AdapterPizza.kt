package com.example.mypizzasmvi.ui.home.pizzaList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypizzasmvi.core.models.PizzaModel
import com.example.mypizzasmvi.core.utils.loadImage
import com.example.mypizzasmvi.core.utils.setOnSingleClickListener
import com.example.mypizzasmvi.core.utils.toMoneyFormat
import com.example.mypizzasmvi.databinding.ItemListPizzaBinding

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
        val binding = ItemListPizzaBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return PixWithBankViewHolder(binding,onClick)
    }

    override fun getItemCount() = filteredDataSet.size

    override fun onBindViewHolder(holder: AdapterPixWithBank.PixWithBankViewHolder, position: Int) {
        holder.bind(filteredDataSet[position])
    }

    inner class PixWithBankViewHolder(private val itemBinding: ItemListPizzaBinding, val onClick: (PizzaModel) -> Unit) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pizza: PizzaModel) {
            itemBinding.txtNamePizza.text = pizza.name
            itemBinding.txtPriceValue.text = pizza.priceP.toMoneyFormat()
            itemBinding.rattingBarPizza.rating = pizza.rating
            loadImage(pizza.imageUrl,itemBinding.imageViewPizza)
            itemBinding.itemListPizzaCard.setOnSingleClickListener {
                onClick(pizza)
            }
        }
    }
}