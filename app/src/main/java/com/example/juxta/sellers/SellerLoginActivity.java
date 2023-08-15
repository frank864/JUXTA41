package com.example.juxta.sellers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {
    private Button loginSellerButton;
    private EditText emailInput,passwordInput;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        emailInput= findViewById(R.id.seller_login_email);
        passwordInput=findViewById(R.id.seller_login_password);
        loginSellerButton=findViewById(R.id.seller_login_btn);
        loadingBar=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();


        loginSellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSeller();
            }
        });


    }

    private void loginSeller() {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();


        if (!password.equals("") && !email.equals("")){
            loadingBar.setTitle("Logging into Account");
            loadingBar.setMessage("Please wait while we are checking your incrediantials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(SellerLoginActivity.this,SellerHomeActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }else {
            Toast.makeText(this, "Pleae complete the logging form", Toast.LENGTH_SHORT).show();
        }
    }
}