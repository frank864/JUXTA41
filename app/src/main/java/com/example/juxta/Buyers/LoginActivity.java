package com.example.juxta.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.Admin.AdminHomeActivity;
import com.example.juxta.Prevalent.Prevalent;
import com.example.juxta.R;
import com.example.juxta.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputPhoneNumber,InputPassword;
    private Button LoginBUtton;
    private ProgressDialog loadingBar;
    private String parentDbName= "Users";
    private CheckBox chkBoxRememberMe;
    private TextView AdminLink,NotAdminLink,ForgetPasswordLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingBar= new ProgressDialog(this);

        InputPhoneNumber= findViewById(R.id.login_phone_number_input);
        InputPassword = findViewById(R.id.login_password_input);
        LoginBUtton = findViewById(R.id.login_btn);
        chkBoxRememberMe= findViewById(R.id.remember_me_chkb);
        AdminLink= findViewById(R.id.admin_panel_link);
        NotAdminLink= findViewById(R.id.not_admin_panel_link);
        ForgetPasswordLink=findViewById(R.id.forget_password_link);
        Paper.init(this);

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, ResetPasswordAcivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });


        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginBUtton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);

                parentDbName ="Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginBUtton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName ="Users";

            }
        });

        LoginBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
      String phone= InputPhoneNumber.getText().toString();
      String password= InputPassword.getText().toString();


        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Phone Number is required...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Inorder to ensure security of your account you require a strong password", Toast.LENGTH_SHORT).show();
        }else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we are checking your incrediantials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone,final String password) {
        if (chkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserPasswordKey, password);
            Paper.book().write(Prevalent.UserPhoneKey,phone);
        }
        final DatabaseReference RootRef;

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.child(parentDbName).child(phone).exists()){

                    Users usersdata = datasnapshot.child(parentDbName).child(phone).getValue(Users.class);

                   if (usersdata.getPhone().equals(phone)){
                       if (usersdata.getPassword().equals(password)){

                          if (parentDbName.equals("Admins")){
                              Toast.makeText(LoginActivity.this, "Logged in successifully admin", Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();

                              Intent intent= new Intent(LoginActivity.this, AdminHomeActivity.class);
                              startActivity(intent);

                          } else if (parentDbName.equals("Users")) {
                              Toast.makeText(LoginActivity.this, "Logged in successifully...", Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();

                              Intent intent= new Intent(LoginActivity.this, HomeActivity.class);
                              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              startActivity(intent);
                          }
                       }
                    }

                }else{
                    Toast.makeText(LoginActivity.this, " Account with this " + phone +" number do not existst", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "You need to create new Account...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}