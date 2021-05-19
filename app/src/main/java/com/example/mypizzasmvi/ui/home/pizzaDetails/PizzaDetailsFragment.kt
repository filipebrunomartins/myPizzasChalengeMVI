package com.example.mypizzasmvi.ui.home.pizzaDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.core.utils.loadImage
import com.example.mypizzasmvi.core.utils.setOnSingleClickListener
import com.example.mypizzasmvi.core.utils.toMoneyFormat
import kotlinx.android.synthetic.main.fragment_details_pizza.*

class PizzaDetailsFragment : Fragment() {

    private val args by navArgs<PizzaDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_pizza, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPizzaDetails()
    }

    private fun setupPizzaDetails() {
        rattingBarPizza.rating = args.pizzaModel.rating
        loadImage(args.pizzaModel.imageUrl,imageViewPizzaDetail)
        textViewNamePizza.text = args.pizzaModel.name
        textViewValuePizza.text = args.pizzaModel.priceP.toMoneyFormat()
        buttonSizeP.background = ResourcesCompat.getDrawable(resources, R.drawable.modal_button_size_pizza_selected, null)
        buttonSizeP.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))

        textViewBuyPizza.setOnSingleClickListener {
            Toast.makeText(requireContext(),"teste",Toast.LENGTH_SHORT).show()
        }
    }

}