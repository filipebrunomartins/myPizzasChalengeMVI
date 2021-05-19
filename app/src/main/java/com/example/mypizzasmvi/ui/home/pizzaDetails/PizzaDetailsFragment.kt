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
import com.example.mypizzasmvi.databinding.FragmentDetailsPizzaBinding

class PizzaDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsPizzaBinding
    private val args by navArgs<PizzaDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPizzaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPizzaDetails()
    }

    private fun setupPizzaDetails() {
        binding.rattingBarPizza.rating = args.pizzaModel.rating
        loadImage(args.pizzaModel.imageUrl,binding.imageViewPizzaDetail)
        binding.textViewNamePizza.text = args.pizzaModel.name
        binding.textViewValuePizza.text = args.pizzaModel.priceP.toMoneyFormat()
        binding.buttonSizeP.background = ResourcesCompat.getDrawable(resources, R.drawable.modal_button_size_pizza_selected, null)
        binding.buttonSizeP.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))

        binding.textViewBuyPizza.setOnSingleClickListener {
            Toast.makeText(requireContext(),"teste",Toast.LENGTH_SHORT).show()
        }
    }

}