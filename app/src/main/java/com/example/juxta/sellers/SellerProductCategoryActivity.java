package com.example.juxta.sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView tshirts,sportsTshirts,femaleDresses,sweathers,glasses,hatsCaps,walletsBagsPurses,shoes,headphonesHeadFree,laptops,watches,mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);
        tshirts= findViewById(R.id.t_shirts);
        sportsTshirts=findViewById(R.id.sports);
        femaleDresses=findViewById(R.id.female_dresses);
        sweathers= findViewById(R.id.sweather);
        glasses=findViewById(R.id.glasses);
        hatsCaps= findViewById(R.id.hats_caps);
        walletsBagsPurses=findViewById(R.id.purses_bags_wallets);
        shoes=findViewById(R.id.shoes);
        headphonesHeadFree=findViewById(R.id.headphones_headfree);
        laptops=findViewById(R.id.laptops_pc);
        watches=findViewById(R.id.watches);
        mobilePhones=findViewById(R.id.mobilephones);




        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "tshirts");
                startActivity(intent);
                finish();
            }
        });


        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "sports Tshirts");
                startActivity(intent);
           }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "female Dresses");
                startActivity(intent);
            }
        });
                sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
               intent.putExtra("category", "Sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });
        walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
               intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
       });
        shoes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Shoes");
               startActivity(intent);
            }
        });
        headphonesHeadFree.setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View view) {
               Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "headphones HeadFree");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) { Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
               intent.putExtra("category", "Watches");
                startActivity(intent);
           }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
               intent.putExtra("category", "MobilePhones");
               startActivity(intent);
            }
        });






    }
}