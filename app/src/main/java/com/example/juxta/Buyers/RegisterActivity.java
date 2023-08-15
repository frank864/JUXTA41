package com.example.juxta.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton=findViewById(R.id.register_btn);
        InputName=findViewById(R.id.register_userName_input);
        InputPhoneNumber=findViewById(R.id.register_phone_number_input);
        InputPassword=findViewById(R.id.register_password_input);
        loadingBar=new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {

        String phone = InputPhoneNumber.getText().toString();
        String name = InputName.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Phone Number is required...", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "UserName is required kindly fill your UserNAme...", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Inorder to ensure security of your account you require a strong password", Toast.LENGTH_SHORT).show();
        }else {
           loadingBar.setTitle("Create Account");
           loadingBar.setMessage("Please wait while we are checking your incrediantials.");
           loadingBar.setCanceledOnTouchOutside(false);
           loadingBar.show();

           ValidatePhoneNumber(name,phone,password);
        }
    }

    private void ValidatePhoneNumber(String name,String phone, String password) {

        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (!(datasnapshot.child("Users").child("phone").exists())){


                   HashMap<String, Object> userdataMap = new HashMap<>();
                   userdataMap.put("phone",phone);
                   userdataMap.put("password",password);
                   userdataMap.put("name",name);

                   RootRef.child("Users").child(phone).updateChildren(userdataMap)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(RegisterActivity.this, "Congratulations! Account created successfully", Toast.LENGTH_SHORT).show();
                                       loadingBar.dismiss();




                                       Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);
                                   }else{
                                       loadingBar.dismiss();
                                       Toast.makeText(RegisterActivity.this, "Network Error try again later...", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });

                }else {
                    Toast.makeText(RegisterActivity.this, "This" +  phone  +"already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Toast.makeText(RegisterActivity.this, "Kindly review the number or use a different phone number", Toast.LENGTH_SHORT).show();


                    Intent intent= new Intent(RegisterActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}