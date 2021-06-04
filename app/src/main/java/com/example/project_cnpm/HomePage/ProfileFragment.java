package com.example.project_cnpm.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.SharedReferences.DataLocalManager;

public class ProfileFragment extends Fragment {
    private TextView email;
    private ImageView img;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void mapping(View v){
        email = v.findViewById(R.id.emailCustomer);
        img = v.findViewById(R.id.imgCustomer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        mapping(v);

        Customer account = DataLocalManager.getAccount();
        if(account != null) {
            Log.d("EEE","email: "+account.toString()+"");
                email.setText(account.getUser().getEmail());

            Glide.with(getContext()).load(account.getAvatar()).into(img);
        }

        return v;
    }
}