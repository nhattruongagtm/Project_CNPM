package com.example.project_cnpm.View.HomePage;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_cnpm.Admin.LoginAdminActivity;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.R;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class HeaderFragment extends Fragment {

    ImageView imgCustomer;
    GoogleSignInClient mGoogleSignInClient;

    ImageView btnMenu;


    public HeaderFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_header, container, false);

        imgCustomer = view.findViewById(R.id.imgCustomer);
        btnMenu = view.findViewById(R.id.home_page_menu);

        Customer account = DataLocalManager.getAccount();
        if(account != null){
            Glide.with(this).load(String.valueOf(account.getAvatar())).into(imgCustomer);
        }


        googleAPI();
        loadCustomer();

        // handler btn menu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_menu);

                setPosition(dialog,150);

                TextView btnRedirectAdmin = dialog.findViewById(R.id.btn_redirect_admin_page);
                btnRedirectAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), LoginAdminActivity.class));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        return view;
    }
    public void setPosition(Dialog dialog,int yValue) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.LEFT;
        param.y = yValue;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    public void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //
                        //  MainActivity.account = null;
                        DataLocalManager.setAccount(null);
                        //getActivity().finish();
                    }
                });
    }
    //login google
    public void googleAPI(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }
    public void loadCustomer(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Log.d("EEE",personEmail+"");

            Glide.with(this).load(String.valueOf(personPhoto)).into(imgCustomer);
        }
    }
}