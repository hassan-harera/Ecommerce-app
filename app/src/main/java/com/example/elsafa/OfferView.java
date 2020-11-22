package com.example.elsafa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Controller.ProductPicturesRecyclerViewAdapter;
import Model.Item;
import Model.Offer.Offer;
import Model.Product.CompleteProduct;

public class OfferView extends AppCompatActivity {

    private FirebaseAuth auth;

    private ImageView wish, cart, back;
    private TextView title, endTime, price;
    private ViewPager2 productPics;
    private String productId;
    private FirebaseFirestore fStore;
    private CompleteProduct product;
    private Offer offer;
    private String categoryName;
    private String offerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        productId = getIntent().getExtras().getString("productId");
        categoryName = getIntent().getExtras().getString("categoryName");
        offerId = getIntent().getExtras().getString("offerId");

        wish = findViewById(R.id.wish);
        back = findViewById(R.id.wish);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        productPics = findViewById(R.id.product_pics);
        endTime = findViewById(R.id.end_Time);
        price = findViewById(R.id.price);

        getTheOffer();
        hide();
    }

    private void getTheOffer() {
        fStore.collection("Offers")
                .document(offerId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        offer = ds.toObject(Offer.class);

                        getTheProductInfo(offer.getCategoryName(), offer.getProductId());

                        new CountDownTimer((offer.getEndTime().getSeconds() - Timestamp.now().getSeconds()) * 1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                long days = millisUntilFinished / 86400000;
                                millisUntilFinished = millisUntilFinished % 86400000;
                                long hours = millisUntilFinished / 3600000;
                                millisUntilFinished = millisUntilFinished % 60000;
                                long miens = millisUntilFinished / 60000;
                                endTime.setText(days + " days " + hours + " hours " + miens + " Miens");
                            }

                            public void onFinish() {
                                endTime.setText("Offer is Expired");
                            }
                        }.start();

                        endTime.setText("Ends In " + ((offer.getEndTime().getSeconds() - Timestamp.now().getSeconds()) / 86400) + " days");
                    }
                });
    }

    private void getTheProductInfo(String categoryName, String productId) {
        fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .document(productId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        product = ds.toObject(CompleteProduct.class);
                        product.setProductId(ds.getId());

                        title.setText(product.getTitle());
                        price.setText(product.getPrice() + " EGP");
                        List<Blob> pics = product.getProductPics();
                        productPics.setAdapter(new ProductPicturesRecyclerViewAdapter(pics, OfferView.this));

                        if (product.getCarts().containsKey(auth.getUid())) {
                            cart.setImageResource(R.drawable.carted);
                        }

                        if (product.getWishes().containsKey(auth.getUid())) {
                            wish.setImageResource(R.drawable.wished);
                        }
                    }
                });
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    public void cartClicked(View view) {
        Map<String, Integer> carts = product.getCarts();
        if (carts.containsKey(auth.getUid())) {
            carts.remove(auth.getUid());
            product.setCarts(carts);

            fStore.collection("Categories")
                    .document(offer.getCategoryName())
                    .collection("Products")
                    .document(offer.getProductId())
                    .update("carts", carts);

            cart.setImageResource(R.drawable.cart);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document(offer.getCategoryName() + offer.getProductId())
                    .delete();

            Toast.makeText(this, "Offer removed from the cart", Toast.LENGTH_SHORT).show();
        } else {
            carts.put(auth.getUid(), 1);
            product.setCarts(carts);

            fStore.collection("Categories")
                    .document(offer.getCategoryName())
                    .collection("Products")
                    .document(offer.getProductId())
                    .update("carts", carts);

            cart.setImageResource(R.drawable.carted);

            Item item = new Item();
            item.setTime(Timestamp.now());
            item.setCategoryName(offer.getCategoryName());
            item.setProductId(offer.getProductId());
            item.setQuantity(1);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document(offer.getCategoryName() + offer.getProductId())
                    .set(item);

            Toast.makeText(this, "Offer Added to the cart", Toast.LENGTH_SHORT).show();
        }
    }

    public void wishClicked(View view) {
        Map<String, Integer> wishList = product.getWishes();

        if (wishList.containsKey(auth.getUid())) {
            wishList.remove(auth.getUid());
            product.setWishes(wishList);

            fStore.collection("Categories")
                    .document(offer.getCategoryName())
                    .collection("Products")
                    .document(offer.getProductId())
                    .update("wishes", wishList);

            wish.setImageResource(R.drawable.wish);


            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document(offer.getCategoryName() + offer.getProductId())
                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(OfferView.this, "Offer Added to the wishList", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            wishList.put(auth.getUid(), 1);
            product.setWishes(wishList);

            fStore.collection("Categories")
                    .document(offer.getCategoryName())
                    .collection("Products")
                    .document(offer.getProductId())
                    .update("wishes", wishList);

            wish.setImageResource(R.drawable.wished);

            Item item = new Item();
            item.setTime(Timestamp.now());
            item.setCategoryName(offer.getCategoryName());
            item.setProductId(offer.getProductId());
            item.setQuantity(1);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document(offer.getCategoryName() + offer.getProductId())
                    .set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(OfferView.this, "Offer removed from the wishList", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void goBack(View view) {
        finish();
    }
}