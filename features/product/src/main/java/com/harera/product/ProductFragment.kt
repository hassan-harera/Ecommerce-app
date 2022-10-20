package com.harera.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.harera.common.base.BaseFragment
import com.harera.common.ui.DoneDialog
import com.harera.common.utils.navigation.Arguments.PRODUCT_ID
import com.harera.product.databinding.FragmentProductViewBinding
import com.harera.repository.domain.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : BaseFragment() {

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var bind: FragmentProductViewBinding
    private lateinit var productsAdapter: ProductsAdapter

    private val doneDialog: DoneDialog by lazy { DoneDialog() }

    val TAG = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val productId = it.getString(PRODUCT_ID)!!
            productViewModel.setProductId(productId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentProductViewBinding.inflate(layoutInflater)
        productsAdapter = ProductsAdapter(
            onProductClicked = {

            }
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel.getWishState()
        productViewModel.getCartState()
        setupProductsAdapter()

        productViewModel.getProduct()

        setupObservers()
    }

    private fun setupObservers() {
        productViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        productViewModel.product.observe(viewLifecycleOwner) {
            handleSuccess()
            updateUI(it)

        }

        productViewModel.exception.observe(viewLifecycleOwner) {
            handleError(it)
        }

        productViewModel.wishState.observe(viewLifecycleOwner) {
            updateWishIcon(state = it)
            handleSuccess()
        }
        productViewModel.products.observe(viewLifecycleOwner) {
            updateProducts(it)
        }

        connectionLiveData.observe(viewLifecycleOwner) {
            productViewModel.updateConnectivity(it)
        }
    }

    private fun updateProducts(products: List<Product>) {
        productsAdapter.setProducts(productList = products)
    }

    private fun setupProductsAdapter() {
        bind.products.adapter = productsAdapter
        bind.products.layoutManager = GridLayoutManager(context, 2)
    }

    private fun updateWishIcon(state: Boolean) {
        if (state)
            bind.wish.setIconResource(R.drawable.wished)
        else
            bind.wish.setIconResource(R.drawable.wish_24)
    }

    private fun updateUI(product: Product) {
        bind.title.text = product.title
        bind.price.text = "${product.price} EGP"
        bind.productPics.adapter =
            ProductPicturesAdapter(product.productPictureUrls)

        setupListener()
    }

    private fun setupListener() {
        bind.cart.setOnClickListener {
            productViewModel.changeCartState()
        }

        bind.wish.setOnClickListener {
            productViewModel.changeWishState()
        }
    }
}