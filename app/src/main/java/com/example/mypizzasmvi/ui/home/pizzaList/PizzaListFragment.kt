package com.example.mypizzasmvi.ui.home.pizzaList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.MviStateFragment
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.core.models.PizzaModel
import com.example.mypizzasmvi.core.utils.showMessageError
import com.example.mypizzasmvi.databinding.FragmentListPizzaBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PizzaListFragment: MviStateFragment<PizzaListState>() {

    private lateinit var binding: FragmentListPizzaBinding
    override val viewModel: PizzaListViewModel by viewModel()

    private val navigation get() = findNavController()

    private lateinit var adapter: AdapterPixWithBank

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPizzaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    override fun fetch() {
        super.fetch()
        viewModel.getListPizza()
    }

    override fun render(state: PizzaListState) = when(state) {
        is PizzaListState.Error -> renderError(state.message)
        is PizzaListState.Success -> renderSuccess(state.listPizza)
        is PizzaListState.Loading -> showLoading()
    }

    private fun setupObservers(){
        binding.editTextSearchPizza.addTextChangedListener{
            try { adapter.filter(it.toString())
            }catch (e : Exception){ e.printStackTrace() }
        }
    }

    private fun showLoading() {
        binding.recyclerViewPixBank.visibility = View.GONE
        binding.progressPizza.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.recyclerViewPixBank.visibility = View.VISIBLE
        binding.progressPizza.visibility = View.GONE
    }

    private fun renderSuccess(loginResponse: List<PizzaModel>) {
        adapter = AdapterPixWithBank(requireContext(), loginResponse, ::navigateDetailsPizza)
        adapter.filter(null)
        binding.recyclerViewPixBank.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPixBank.adapter = adapter
        hideLoading()
    }

    private fun renderError(message: String) {
        //TODO TRATAR ERRO LISTA VAZIA
        hideLoading()
        showMessageError(message, getString(R.string.attention_text))
    }

    private fun navigateDetailsPizza(pizza: PizzaModel) {
        try {
            navigation.navigate(PizzaListFragmentDirections.actionPizzaListFragmentToPizzaDetailsFragment(pizza))
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}