package com.example.project_cnpm.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_cnpm.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    RecyclerView recyclerToday;
    RecyclerView recyclerRecommended;
    GoogleSignInClient mGoogleSignInClient;
//    ImageView imgCustomer;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


         recyclerToday = view.findViewById(R.id.home_page_dishToday_recyler);

         recyclerRecommended = view.findViewById(R.id.home_page_dishRecommended);

//        imgCustomer = view.findViewById(R.id.imgCustomer);

        // khởi tạo recyler view today
        createDishesToday();
        // khởi tạo recyler view recommend
        createDishesRecommended();

//
//        googleAPI();
//        loadCustomer(view);

        // load img
//        Customer account = DataLocalManager.getAccount();
//        if(account != null){
//            Glide.with(this).load(String.valueOf(account.getAvatar())).into(imgCustomer);
//        }



//        // sign out
//        imgCustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataLocalManager.setValid(false);
//                signOut();
//            }
//        });

        return view;


    }
//    public void signOut(){
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        //
//                      //  MainActivity.account = null;
//                        DataLocalManager.setAccount(null);
//                        //getActivity().finish();
//                    }
//                });
//    }
//    //login google
//    public void googleAPI(){
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
//    }
//    public void loadCustomer(View view){
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
//            Log.d("EEE",personEmail+"");
//
//            Glide.with(this).load(String.valueOf(personPhoto)).into(imgCustomer);
//        }
//    }
    public void createDishesToday(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerToday.setLayoutManager(layoutManager);

        DishTodayAdapter adapter = new DishTodayAdapter(getActivity(),getDishesToday());
        recyclerToday.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    public void createDishesRecommended(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerRecommended.setLayoutManager(layoutManager);

        DishRecommendAdapter adapter = new DishRecommendAdapter(getActivity(),getDishesToday());
        recyclerRecommended.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public ArrayList<Dish> getDishesToday(){
        ArrayList<Dish> list = new ArrayList<>();
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        return list;
    }

}