package com.example.project_cnpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.project_cnpm.HomePage.AboutFragment;
import com.example.project_cnpm.HomePage.Dish;
import com.example.project_cnpm.HomePage.DishRecommendAdapter;
import com.example.project_cnpm.HomePage.DishTodayAdapter;
import com.example.project_cnpm.HomePage.HomeFragment;
import com.example.project_cnpm.HomePage.NotificationFragment;
import com.example.project_cnpm.Login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    // google api
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ánh xạ
        mapping();

        // thêm bottom menu
        addMenuItem();

        // load customer sau khi đăng nhập
        googleAPI();

    }


    public void mapping(){
        bottomNavigation = findViewById(R.id.navigation_bottom);


    }
    public void addMenuItem(){
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_notifications_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_account_circle_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 2: fragment = new NotificationFragment();
                    break;
                    case 1: fragment = new HomeFragment();
                    break;
                    case 3: startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    break;
                }
                if (fragment != null) {
                    loadFragment(fragment);
                }
            }
        });

        // set notification count
        bottomNavigation.setCount(2,"10");
        //set home fragment initially selected
        bottomNavigation.show(1,true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                // toast
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // display toast
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // replace fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_homepage,fragment).commit();
    }
    // sign out
    public void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        finish();
                    }
                });
    }
    //login google
    public void googleAPI(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


}