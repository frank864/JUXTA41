package com.example.juxta.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juxta.Interface.ItemClickListerner;
import com.example.juxta.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtproductionName,txtproductionDescription,txtproductPrice;
    public ImageView imageView;
    public ItemClickListerner listerner;

    @Override
    public void onClick(View view) {
        listerner.onClick(view, getAdapterPosition(),false);

    }

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=itemView.findViewById(R.id.product_image);
        txtproductionName=itemView.findViewById(R.id.product_name);
        txtproductionDescription=itemView.findViewById(R.id.product_description);
        txtproductPrice=itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListerner(ItemClickListerner listerner){
        this.listerner=listerner;
    }
}
