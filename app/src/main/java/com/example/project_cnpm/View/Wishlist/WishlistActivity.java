package com.example.project_cnpm.View.Wishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project_cnpm.R;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView list;
    private ArrayList<Wishlist> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        list = findViewById(R.id.listRe);

        products = new ArrayList<Wishlist>();
        products.add(new Wishlist("Bread","Bread with speacial sauce",3.0,1,R.drawable.bread));
        products.add(new Wishlist("Hamburger","Hamburger is bigest",1.5,2,R.drawable.hamburger));
        products.add(new Wishlist("Chicken fried","Chicken fried delisious",1.0,1,R.drawable.chickenfried));

        MyAdapter myAdapter = new MyAdapter(this,products);
        list.setAdapter(myAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));



    }


}