package com.example.juxta.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.Prevalent.Prevalent;
import com.example.juxta.R;
import com.example.juxta.model.Users;
import com.example.juxta.sellers.SellerHomeActivity;
import com.example.juxta.sellers.SellerRegistrationAcivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button joinNowButton,loginButton;
    private ProgressDialog loadingBar;
    private TextView sellerBegin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingBar = new ProgressDialog(this);

        joinNowButton=findViewById(R.id.main_join_now_btn);
        loginButton=findViewById(R.id.main_login_btn);
        sellerBegin=findViewById(R.id.seller_begin);
        Paper.init(this);


        sellerBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, SellerRegistrationAcivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        String UserPhoneKey =  Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);


        if (UserPhoneKey != "" && UserPasswordKey != ""){
           if (!TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserPhoneKey)) {
               AllowAccess(UserPhoneKey, UserPasswordKey);

               loadingBar.setTitle("Please wait you are already logged in.... ");
               loadingBar.setMessage("Please wait ...");
               loadingBar.setCanceledOnTouchOutside(false);
               loadingBar.show();
           }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent intent= new Intent(MainActivity.this, SellerHomeActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    private void AllowAccess(final String phone, final String password) {



        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.child("Users").child(String.valueOf(phone)).exists()){

                    Users usersdata = datasnapshot.child("Users").child(String.valueOf(phone)).getValue(Users.class);

                    if (usersdata.getPhone().equals(phone)){
                        if (usersdata.getPassword().equals(password)){

                            Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Prevalent.currentOnlineUser= usersdata;
                            startActivity(intent);

                        }
                    }

                }else{
                    Toast.makeText(MainActivity.this, " Account with this " + phone+" do not existst", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "You need to create new Account...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}