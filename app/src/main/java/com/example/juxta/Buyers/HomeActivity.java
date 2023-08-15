package com.example.juxta.Buyers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juxta.Admin.AdminMaintainProductActivity;
import com.example.juxta.Prevalent.Prevalent;
import com.example.juxta.R;
import com.example.juxta.ViewHolder.ProductViewHolder;
import com.example.juxta.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cart){
            if (!type.equals("Admin")){
                Intent intent= new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }


        } else if (id ==R.id.nav_search) {
            if (!type.equals("Admin")){
                Intent intent= new Intent(HomeActivity.this, SearchProductsActivity.class);
                startActivity(intent);
            }


        } else if (id ==R.id.nav_setting) {
            if (!type.equals("Admin")){
                Intent intent= new Intent(HomeActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }




        } else if (id ==R.id.nav_categories) {

            if (!type.equals("Admin")){

            }

        } else if (id == R.id.nav_logout) {
            if (!type.equals("Admin")){

                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("LOGOUT")
                        .setIcon(R.drawable.ic_lgouticonic)
                        .setMessage("Are you sure you want to logout from our App???...")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Paper.book().destroy();
                                Intent intent= new Intent(HomeActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                Toast.makeText(HomeActivity.this, "See you soon!!!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }).create().show();
            }


        }
        return false;
    }

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private String type ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            type=getIntent().getExtras().get("Admin").toString();
        }

        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        fab = findViewById(R.id.fab);
        Paper.init(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);

       if (!type.equals("Admin")){
           userNameTextView.setText(Prevalent.currentOnlineUser.getName());
           Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);


       }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.equals("Admin")){
                    Intent intent= new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options= new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.orderByChild("productstate").equalTo("Approved"), Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                holder.txtproductionName.setText(model.getPname());
                holder.txtproductionDescription.setText(model.getDescription());
                holder.txtproductPrice.setText("Price = " +model.getPrice() + "KSH");

                Picasso.get().load(model.getImage()).into(holder.imageView);



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (type.equals("Admin")){
                            Intent intent= new Intent(HomeActivity.this, AdminMaintainProductActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        }else {
                            Intent intent= new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        }
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false);
                ProductViewHolder holder= new ProductViewHolder(view);
                return  holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();













    }

}