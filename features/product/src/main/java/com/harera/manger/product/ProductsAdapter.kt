package com.harera.manger.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.manger.product.databinding.CardViewProductBinding
import com.harera.repository.domain.Product
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking

class ProductsAdapter(
    private var products: List<Product> = emptyList(),
    private val onProductClicked: (productId: String) -> Unit,
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(productList: List<Product>) = runBlocking {
        products = productList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewProductBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateView(product: Product) {
            Picasso.get().load(product.productPictureUrls.first()).fit().into(bind.image)
            bind.price.text = "${product.price} EGP"
            bind.title.text = product.title

            bind.root.setOnClickListener {
                onProductClicked(
                    product.productId
                )
            }
        }
    }
}