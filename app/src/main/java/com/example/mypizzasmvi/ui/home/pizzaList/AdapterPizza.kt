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

    private lateinit var binding: ItemListPizzaBinding
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
        binding = ItemListPizzaBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return PixWithBankViewHolder(binding,onClick)
    }

    override fun getItemCount() = filteredDataSet.size

    override fun onBindViewHolder(holder: AdapterPixWithBank.PixWithBankViewHolder, position: Int) {
        holder.bind(filteredDataSet[position])
    }

    inner class PixWithBankViewHolder(binding: ItemListPizzaBinding, val onClick: (PizzaModel) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pizza: PizzaModel) {
            binding.txtNamePizza.text = pizza.name
            binding.txtPriceValue.text = pizza.priceP.toMoneyFormat()
            binding.rattingBarPizza.rating = pizza.rating
            loadImage(pizza.imageUrl,binding.imageViewPizza)
            binding.itemListPizzaCard.setOnSingleClickListener {
                onClick(pizza)
            }
        }
    }
}