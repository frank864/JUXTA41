package com.example.juxta.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.Buyers.HomeActivity;
import com.example.juxta.Buyers.MainActivity;
import com.example.juxta.R;

public class AdminHomeActivity extends AppCompatActivity {
    private Button LogoutBtn,checkOrderBtn,maintainProductBtn,checkApprovedProductsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        LogoutBtn= findViewById(R.id.admin_logout_btn);
        checkOrderBtn=findViewById(R.id.check_orders_btn);
        maintainProductBtn=findViewById(R.id.maintain_btn);
        checkApprovedProductsBtn=findViewById(R.id.check_approved_products_btn);


        maintainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        checkOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });
        checkApprovedProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHomeActivity.this, CheckNewProductsActivity.class);
                startActivity(intent);

            }
        });
    }
}