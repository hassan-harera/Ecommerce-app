package com.harera.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.harera.common.base.BaseFragment
import com.harera.model.model.Product
import com.harera.components.product.ProductsAdapter
import com.harera.search.databinding.FragmentSearchBinding


class SearchFragment : BaseFragment() {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var bind: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSearchBinding.inflate(layoutInflater)
        productsAdapter = ProductsAdapter(
            onProductClicked = {
//                TODO
            }
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupProductsAdapter()

        searchViewModel.products.observe(viewLifecycleOwner) {
            setProducts(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            searchViewModel.updateConnectivity(it)
        }
    }

    private fun setProducts(products: List<Product>) {
        productsAdapter.setProducts(productList = products)
    }

    private fun setupProductsAdapter() {
        bind.products.setHasFixedSize(true)
        bind.products.layoutManager = GridLayoutManager(context, 2)
        bind.products.adapter = productsAdapter
    }
}