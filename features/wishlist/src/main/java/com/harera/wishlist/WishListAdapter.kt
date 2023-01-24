package com.harera.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.wishlist.databinding.CardViewWishItemBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class WishListAdapter @Inject constructor(
    private var list: List<WishItem> = emptyList(),
    private val onRemoveItemClicked : (String) -> Unit,
    private val onAddToCartClicked : (String) -> Unit,
    private val onItemClicked : (String) -> Unit,
) : RecyclerView.Adapter<WishListAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            CardViewWishItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.updateView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateWishList(wishItem: List<WishItem>) {
        list = wishItem
    }

    inner class CartItemViewHolder(private val bind: CardViewWishItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun updateView(item: WishItem) {

            Picasso.get().load(item.productImageUrl).fit().into(bind.itemImage)
            bind.title.text = item.productTitle

            bind.addToCart.setOnClickListener {
                onAddToCartClicked(item.productId)
            }

            bind.remove.setOnClickListener {
                onRemoveItemClicked(item.productId)
            }

            bind.itemImage.setOnClickListener {
                onItemClicked(item.productId)
            }
        }
    }
}