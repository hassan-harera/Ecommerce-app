package com.harera.features.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.features.cart.databinding.CardViewCartItemBinding
import com.harera.repository.domain.CartItem
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartAdapter @Inject constructor(
    private var list: List<CartItem> = ArrayList(),
    private val onRemoveItemClicked: (String) -> Unit,
    private val onItemClicked: (String) -> Unit,
    private val onMoveToFavouriteClicked: (String) -> Unit,
    private val onQuantityPlusClicked: (String) -> Unit,
    private val onQuantityMinusClicked: (String) -> Unit,
) : RecyclerView.Adapter<CartAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            CardViewCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.updateView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setCartList(cartList: List<CartItem>) {
        this.list = cartList
        notifyDataSetChanged()
    }

    inner class CartItemViewHolder(private val bind: CardViewCartItemBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateView(item: CartItem) {
            updateDetails(item)

            bind.root.setOnClickListener {
                onItemClicked(item.productId)
            }

            bind.plus.setOnClickListener {
                item.quantity += 1
                updateDetails(item)
                onQuantityPlusClicked(item.cartItemId)
            }

            bind.minus.setOnClickListener {
                if (bind.quantity.text == "1")
                    onRemoveItemClicked(item.cartItemId)
                else {
                    item.quantity -= 1
                    updateDetails(item)
                    onQuantityMinusClicked(item.cartItemId)
                }
            }

            bind.moveToFav.setOnClickListener {
                onMoveToFavouriteClicked(item.cartItemId)
            }

            bind.remove.setOnClickListener {
                onRemoveItemClicked(item.cartItemId)
            }
        }

        private fun updateDetails(item: CartItem) {
            Picasso.get().load(item.productImageUrl).fit().into(bind.itemImage)
            bind.title.text = item.productTitle
            bind.quantity.text = item.quantity.toString()
            bind.price.text = "${item.productPrice * item.quantity}"
        }
    }
}