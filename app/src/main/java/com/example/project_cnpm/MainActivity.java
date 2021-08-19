package com.example.project_cnpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.project_cnpm.DishesPage.DishesView;
import com.example.project_cnpm.HomePage.HomeFragment;
import com.example.project_cnpm.HomePage.UserFragment;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.example.project_cnpm.Utility.NetworkChangeListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    //kiểm tra kết nối internet
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

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

        //
        Customer account = DataLocalManager.getAccount();
        if(account != null) {
            Log.d("ZZZ", account.toString());
        }
        else{
            Log.d("ZZZ", "account ================= null");
        }

    }


    public void mapping(){
        bottomNavigation = findViewById(R.id.navigation_bottom);

      //  DataLocalManager.setValid(false);

//        Intent intent = getIntent();
//        account = (Customer)intent.getSerializableExtra("account");
//        setAccount(account);
      //  DataLocalManager.getAccount()

    }
    public void addMenuItem(){
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_set_meal_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_account_circle_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 2: fragment = new DishesView();
                    break;
                    case 1: fragment = new HomeFragment();
                    break;
                    case 3: fragment = new UserFragment();

                    break;
                }
                if (fragment != null) {
                    loadFragment(fragment);
                }
            }
        });

//        // set notification count
//        bottomNavigation.setCount(2,"10");
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
                      //  account = null;
                        DataLocalManager.setAccount(null);
                        DataLocalManager.setValid(false);
                        finish();
                    }
                });
    }
    //login google
    public void googleAPI(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}