package com.harera.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.image_slider.databinding.CardViewImageBinding
import com.harera.model.model.Offer
import com.squareup.picasso.Picasso

class OffersAdapter(
    private var offers: List<Offer> = emptyList(),
    private val onOfferClicked: (String) -> Unit
) : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUI(offer = offers[position])
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    fun setOffers(offers: List<Offer>) {
        this.offers = offers
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewImageBinding) : RecyclerView.ViewHolder(bind.root) {
        fun updateUI(offer: Offer) {
            Picasso.get().load(offer.offerImageUrl).fit().into(bind.image)

            bind.image.setOnClickListener {
                onOfferClicked(offer.offerId)
            }
        }
    }
}
