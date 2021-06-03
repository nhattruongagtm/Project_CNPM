package com.example.project_cnpm.HomePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_cnpm.Login.LoginActivity;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.SharedReferences.DataLocalManager;


public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);

        Fragment fragment = null;
//        Customer account = MainActivity.account;
        Customer account = DataLocalManager.getAccount();
        if (account == null){
          //  fragment = new LoginFragment();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        else{
            fragment = new ProfileFragment();
        }
        if(fragment != null){
            loadFragment(fragment);
        }

        return view;
    }
    public void loadFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_user,fragment).commit();
    }
}