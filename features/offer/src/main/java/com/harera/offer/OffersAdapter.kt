package com.harera.offer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.model.model.Offer
import com.harera.offer.databinding.CardViewOfferBinding
import com.squareup.picasso.Picasso

class OffersAdapter(private var offers: List<Offer>) :
    RecyclerView.Adapter<OffersAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(offers[position].offerImageUrl).fit().into(holder.bind.image)
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    fun setOffers(offers: List<Offer>) {
        this.offers = offers
    }

    inner class ViewHolder(val bind:  CardViewOfferBinding) : RecyclerView.ViewHolder(bind.root)
}