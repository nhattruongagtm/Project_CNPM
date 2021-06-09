package com.example.project_cnpm.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Controller.ILoginController;
import com.example.project_cnpm.Controller.LoginController;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.R;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.example.project_cnpm.SignUp.SignUpActivity;

import com.example.project_cnpm.View.ILoginView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
   // private AccessTokenTracker accessTokenTracker;

    ILoginController loginController;


    //google api
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 0;
    TextView btnChangeSignUp;
    LinearLayout btnBack;

    // khai báo đăng nhập bầng username và password
    private EditText email, password;
    private Button btnLogin;
    private CheckBox btnSave;
    private TextView notify;
    private boolean saveInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mapping();

        loginController = new LoginController(this,new LoginDAO(this));

        btnChangeSignUp = findViewById(R.id.changeSignUp);
        btnBack = findViewById(R.id.btnBack_hompage);

        btnChangeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        if (DataLocalManager.getLoginInput() != null){

        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        btnSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveInput = isChecked;
            }
        });
        if(DataLocalManager.getLoginInput() != null) {
            String m = "", p = "";
            for (Map.Entry<String, String> map : DataLocalManager.getLoginInput().entrySet()) {
                m = map.getKey();
                p = map.getValue();

                if (!map.getKey().equals("") || !map.getKey().equals("")) {
                    btnSave.setChecked(true);
                }
                else{
                    btnSave.setChecked(false);
                }
                Log.d("AAA", m+"  ----  "+p);
            }
            email.setText(m + "");
            password.setText(p + "");

        }


        // facebook api
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);

        loginButton.setPermissions("email");
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        Log.d("AAA",object.toString());
//                        try {
//                            Customer account = new Customer();
//                            User user = new User();
//                            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                                @Override
//                                public void onCompleted(JSONObject object, GraphResponse response) {
//                                    Log.d("AAA",object.toString());
//                                    try {
//                                        String name = object.getString("name");
//                                        String id = object.getString("id");
//                                        String avatar = "https://graph.facebook.com/"+id+"/picture?type=large";
//                                        account.setName(name);
//                                        account.setIdCustomer(id);
//                                        account.setAvatar(avatar);
//
//                                        user.setEmail(id);
//                                        account.setUser(user);
//                                        Log.d("AAA", user.toString());
//
//                                        DataLocalManager.setAccount(account);
//
//                                        if (DataLocalManager.getAccount()!= null){
//                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                                        }
//
//                                        Log.d("AAA","Login successful!");
//                                        Log.d("AAA",account.getName());
//
////                                        accessTokenTracker = new AccessTokenTracker() {
////                                            @Override
////                                            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
////                                                if(currentAccessToken == null){
////                                                    LoginManager.getInstance().logOut();
////                                                }
////                                            }
////                                        };
//
//                                    }
//                                    catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                            Bundle bundle= new Bundle();
//                            bundle.putString("fields","gender, name, id, first_name, last_name");
//                            graphRequest.setParameters(bundle);
//                            graphRequest.executeAsync();
//
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                Bundle bundle= new Bundle();
//                bundle.putString("fields","gender, name, id, first_name, last_name");
//                graphRequest.setParameters(bundle);
//                graphRequest.executeAsync();
//            }
//        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Customer account = new Customer();
                String id = loginResult.getAccessToken().getUserId();

                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("AAA",object.toString());
                        try {
                            String name = object.getString("name");
                            account.setName(name);
                            String id = object.getString("id");
                            String avatar = "https://graph.facebook.com/"+id+"/picture?type=large";
                            account.setIdCustomer(id);
                            account.setAvatar(avatar);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                Bundle bundle= new Bundle();
                bundle.putString("fields","gender, name, id, first_name, last_name");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

                account.setIdCustomer(id);
                User user = new User();
                user.setEmail(id);
                user.setPassword("");
                user.setStatus(1);
                account.setUser(user);
                DataLocalManager.setAccount(account);

                if (DataLocalManager.getAccount()!= null){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }

                Log.d("AAA","Login successful!");
              //  Log.d("AAA",account.getName());
            }

            @Override
            public void onCancel() {
                Log.d("AAA","Login canceled!");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("AAA","Login error!");
            }
        });

        //google api
        loginGoogle();

        // đăng nhập bằng username, password
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String mail = email.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    Log.d("CCC",mail+"-----"+pass);
                    MD5 md5 = new MD5();
                   // login(mail,md5.enryptPassword(pass));

                    if(loginController.login(mail,md5.enryptPassword(pass))){
                         if (saveInput){
                                DataLocalManager.setSaveAccount(email.getText().toString(),password.getText().toString());
                         }
                        else{
                            DataLocalManager.setSaveAccount(null,null);
                        }
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                    Log.d("RRR","check: "+ loginController.login(mail,md5.enryptPassword(pass))+"");

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public void mapping(){
        email = findViewById(R.id.login_username_w);
        password = findViewById(R.id.login_password_w);
        btnSave = findViewById(R.id.login_save_password);
        btnLogin = findViewById(R.id.login_btn_login);
        notify = findViewById(R.id.notify_fail_login);
        btnSave = findViewById(R.id.checkbox_save_input);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
         //facebook
//        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                Log.d("AAA",object.toString());
//                try {
//                    String name = object.getString("name");
//                    Log.d("XXX",name);
//
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//        Bundle bundle= new Bundle();
//        bundle.putString("fields","gender, name, id, first_name, last_name");
//        graphRequest.setParameters(bundle);
//        graphRequest.executeAsync();



        // google
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null){
                LoginManager.getInstance().logOut();
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.startTracking();
    }
    public void loginGoogle(){
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Intent intent = new Intent(this,MainActivity.class);
            User user = new User();
            user.setEmail(account.getEmail());
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setName(account.getDisplayName());
            customer.setAvatar(account.getPhotoUrl()+"");

            DataLocalManager.setAccount(customer);

            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AAA", "signInResult:failed code=" + e.getStatusCode());
        }
    }


    @Override
    public void showLoginSuccess(String message) {
        notify.setTextColor(Color.GREEN);
        notify.setText(message);
    }

    @Override
    public void showLoginFail(String message) {
        notify.setTextColor(Color.RED);
        notify.setText(message);
    }
}