package com.harera.categories

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.harera.categories.databinding.FragmentCategoriesBinding
import com.harera.common.base.BaseFragment
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.components.product.ProductsAdapter
import com.harera.model.model.Category
import com.harera.model.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriesFragment : BaseFragment() {
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var bind: FragmentCategoriesBinding

    private val TAG = "CategoriesFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            it.getString(Arguments.CATEGORY_ID)?.let { category ->
                if (category != "null")
                    categoriesViewModel.setCategoryName(category)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = FragmentCategoriesBinding.inflate(layoutInflater)
        productsAdapter = ProductsAdapter(
            onProductClicked = { productId ->
                findNavController().navigate(
                    Uri.parse(
                        NavigationUtils.getUriNavigation(
                            Arguments.HYPER_PANDA_DOMAIN,
                            Destinations.PRODUCT,
                            productId
                        )
                    )
                )
            }
        )

        categoriesAdapter = CategoriesAdapter(
            onCategoryClicked = {
                categoriesViewModel.setCategoryName(it)
            }
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesAdapter()
        setupProductsAdapter()

        setupObservers()

        lifecycleScope.launch(Dispatchers.IO) {
            categoriesViewModel.getCategories()
            categoriesViewModel.getProducts()
        }
    }

    private fun setupObservers() {
        categoriesViewModel.categories.observe(viewLifecycleOwner) {
            updateCategoriesView(categories = it)
            Log.d(TAG, "setupObservers: ${it.size}")
        }

        categoriesViewModel.products.observe(viewLifecycleOwner) {
            updateProductsView(it)
        }

        categoriesViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(state = it)
        }

        categoriesViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        categoriesViewModel.categoryName.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                categoriesViewModel.getProducts()
            }
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            categoriesViewModel.updateConnectivity(it)
        }
    }

    private fun updateProductsView(products: List<Product>) {
        productsAdapter.setProducts(products)
    }

    private fun updateCategoriesView(categories: List<Category>) {
        categoriesAdapter.setCategories(categories)
    }

    private fun setupCategoriesAdapter() {
        bind.categories.setHasFixedSize(true)
        bind.categories.adapter = categoriesAdapter
        bind.categories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupProductsAdapter() {
        bind.products.setHasFixedSize(true)
        bind.products.adapter = productsAdapter
        bind.products.layoutManager = GridLayoutManager(context, 2)
    }
}