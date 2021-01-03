package com.whiteside.cafe.Offer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.whiteside.cafe.Shop.OnGetOffersListener;

import Model.Offer;

public class OfferPresenter {

    private final OnGetOffersListener listener;
    private Offer offer;

    public OfferPresenter(OnGetOffersListener listener) {
        this.listener = listener;
    }

    private void getOfferFromFirebase(String offerId) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        fStore.collection("Offers")
                .document(offerId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        offer = ds.toObject(Offer.class);
                        offer.setOfferId(ds.getId());
                        listener.onGetOfferSuccess(offer);
                    }
                });
    }

    public void getOffer(String offerId) {
        getOfferFromFirebase(offerId);
    }
}