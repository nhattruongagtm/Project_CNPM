package com.example.project_cnpm.View.HomePage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_cnpm.View.Login.LoginActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileFragment extends Fragment {
    private TextView name, id;
    private ImageView img;
    private TextView btnSignout;

    GoogleSignInClient mGoogleSignInClient;


    public ProfileFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void mapping(View v){
        name = v.findViewById(R.id.emailCustomer);
        img = v.findViewById(R.id.imgCustomer);
        btnSignout = v.findViewById(R.id.logout);
        id = v.findViewById(R.id.email1Customer);
    }
    public void createDialogSignOut(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_signout);
        Button btnY, btnN;
        btnN = dialog.findViewById(R.id.no_signout);
        btnY = dialog.findViewById(R.id.yes_signout);

        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                dialog.dismiss();
            }
        });

        dialog.show();

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
                name.setText(account.getName());
                id.setText(account.getUser().getEmail());

            Glide.with(getContext()).load(account.getAvatar()).into(img);
        }

        googleAPI();

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogSignOut();
            }
        });

        return v;
    }
    public void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //
                        //  MainActivity.account = null;


                        //getActivity().finish();

                    }
                });
        DataLocalManager.setAccount(null);
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
    //login google
    public void googleAPI(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }
}