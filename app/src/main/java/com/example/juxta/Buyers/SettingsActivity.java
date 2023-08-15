package com.example.juxta.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.juxta.Prevalent.Prevalent;
import com.example.juxta.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private EditText fullNameEditText,userPhoneEditText,addressEditText;
    private TextView profileChangeTextBtn,closeTextBtn,saveTextButton;
    private Uri imageUrl;
    private String myUrl ="";
    private StorageTask uploadtask;
    private StorageReference storageProfilePictureRef;
    private String checker="";
    private Button securityQuestionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        securityQuestionBtn=findViewById(R.id.security_question_Btn);
        profileImageView= findViewById(R.id.settings_profile_image);
        fullNameEditText=findViewById(R.id.settings_full_name);
        userPhoneEditText=findViewById(R.id.settings_phone_number);
        addressEditText=findViewById(R.id.settings_address);

        profileChangeTextBtn=findViewById(R.id.profile_image_change_btn);
        closeTextBtn=findViewById(R.id.close_settings_btn);
        saveTextButton=findViewById(R.id.update_account_setting_btn);


        userInfoDisplay(profileImageView,fullNameEditText,userPhoneEditText,addressEditText);

        securityQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SettingsActivity.this, ResetPasswordAcivity.class);
                intent.putExtra("check","settings");
                startActivity(intent);
            }
        });

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")){
                    userInfoSaved();
                }else {
                    updateOnlyUserInfo();
                }
            }
        });
        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker="clicked";









            }
        });
    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");


        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameEditText);
        userMap.put("address", addressEditText);
        userMap.put("phoneOrder", userPhoneEditText);
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);




        startActivity(new Intent(SettingsActivity.this, MainActivity.class));

        Toast.makeText(SettingsActivity.this, "Profile information Updated successfully...", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void userInfoSaved() {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString())){
            Toast.makeText(this, "Name is Mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "Address is required...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())) {
            Toast.makeText(this, "Phone number is Mandatory", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }


    }

    private void uploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while we are updating your Account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        if (imageUrl != null){
            final StorageReference fileRef = storageProfilePictureRef.child(Prevalent.currentOnlineUser.getPhone() + ".jpg");
            uploadtask = fileRef.putFile(imageUrl);


            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downloadUrl =task.getResult();
                                myUrl=downloadUrl.toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");


                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap.put("name", fullNameEditText);
                                userMap.put("address", addressEditText);
                                userMap.put("phoneOrder", userPhoneEditText);
                                userMap.put("image", myUrl);
                                ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

                                progressDialog.dismiss();


                                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));

                                Toast.makeText(SettingsActivity.this, "Profile information Updated successfully...", Toast.LENGTH_SHORT).show();
                                finish();


                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(CircleImageView profileImageView, EditText fullNameEditText, EditText userPhoneEditText, EditText addressEditText) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()){
                    if (datasnapshot.child("image").exists()){
                        String image = datasnapshot.child("image").getValue().toString();
                        String name = datasnapshot.child("name").getValue().toString();
                        String phone = datasnapshot.child("phone").getValue().toString();
                        String address = datasnapshot.child("address").getValue().toString();


                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}