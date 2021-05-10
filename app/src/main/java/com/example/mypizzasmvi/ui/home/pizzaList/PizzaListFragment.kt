package com.example.mypizzasmvi.ui.home.pizzaList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi.MviStateFragment
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.core.models.PizzaModel
import com.example.mypizzasmvi.core.utils.showMessageError
import kotlinx.android.synthetic.main.fragment_list_pizza.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PizzaListFragment: MviStateFragment<PizzaListState>() {

    override val viewModel: PizzaListViewModel by viewModel()

    private val navigation get() = findNavController()

    private lateinit var adapter: AdapterPixWithBank

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_pizza, container, false)
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
        editTextSearchPizza.addTextChangedListener{
            try { adapter.filter(it.toString())
            }catch (e : Exception){ e.printStackTrace() }
        }
    }

    private fun showLoading() {
        recyclerViewPixBank.visibility = View.GONE
        progressPizza.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        recyclerViewPixBank.visibility = View.VISIBLE
        progressPizza.visibility = View.GONE
    }

    private fun renderSuccess(loginResponse: List<PizzaModel>) {
        adapter = AdapterPixWithBank(requireContext(), loginResponse, ::navigateDetailsPizza)
        adapter.filter(null)
        recyclerViewPixBank.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewPixBank.adapter = adapter
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