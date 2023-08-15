package com.example.juxta.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juxta.Interface.ItemClickListerner;
import com.example.juxta.R;


public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtproductionName,txtproductionDescription,txtproductPrice,txtProductsStatus;
    public ImageView imageView;
    public ItemClickListerner listerner;

    @Override
    public void onClick(View view) {
        listerner.onClick(view, getAdapterPosition(),false);

    }

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=itemView.findViewById(R.id.product_seller_image);
        txtproductionName=itemView.findViewById(R.id.product_seller_name);
        txtproductionDescription=itemView.findViewById(R.id.product_seller_description);
        txtproductPrice=itemView.findViewById(R.id.product_seller_price);
        txtProductsStatus=itemView.findViewById(R.id.seller_product_status);
    }

    public void setItemClickListerner(ItemClickListerner listerner){
        this.listerner=listerner;
    }
}

