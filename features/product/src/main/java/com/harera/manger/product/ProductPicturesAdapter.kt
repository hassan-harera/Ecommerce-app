package com.harera.manger.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.manger.product.databinding.CardViewImageBinding
import com.squareup.picasso.Picasso

class ProductPicturesAdapter(private val pictures: List<String>) :
    RecyclerView.Adapter<ProductPicturesAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(pictures[position]).fit().into(holder.bind.image)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    inner class ViewHolder(val bind: CardViewImageBinding) : RecyclerView.ViewHolder(bind.root)
}