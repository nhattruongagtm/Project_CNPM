package com.example.project_cnpm.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Controller.ILoginController;
import com.example.project_cnpm.Controller.LoginController;
import com.example.project_cnpm.DAO.LoginDAO;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    ILoginController loginController;

    //google api
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 0;
    TextView btnChangeSignUp;
    public HashMap<String,String> accounts = new HashMap<>();
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
        getAllAccount();

        loginController = new LoginController(this,new LoginDAO(this));

        btnChangeSignUp = findViewById(R.id.changeSignUp);
        btnBack = findViewById(R.id.btnBack_hompage);

        btnChangeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
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
        if(notify.getText().toString().equals("*Đăng nhập thành công!")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

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
        // sign up thành công
        Intent intent = getIntent();
        if (intent.getStringExtra("email_signup") != null){
            email.setText(intent.getStringExtra("email_signup"));
            password.setText("");
            btnSave.setChecked(false);
            Toast.makeText(this, intent.getStringExtra("notify"), Toast.LENGTH_SHORT).show();
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
                User user = new User();

                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("AAA",object.toString());
                        try {
                            String name = object.getString("name");
                            String id = object.getString("id");
                            String avatar = "https://graph.facebook.com/"+id+"/picture?type=large";
                            account.setName(name);
                            account.setIdCustomer(id);
                            account.setAvatar(avatar);

                            user.setEmail(id);
                            account.setUser(user);

                            DataLocalManager.setAccount(account);

                            //create Account
                            String url = "https://appfooddb.000webhostapp.com/checkAccountAPIUser.php";
                            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.trim().equals("false")){
                                                createAccount(id,name,avatar);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }){
                                @Nullable
                                @org.jetbrains.annotations.Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> params = new HashMap<>();
                                    params.put("idCustomer",account.getIdCustomer());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);

                            if (DataLocalManager.getAccount()!= null){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }
                            Log.d("AAA", user.toString());

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

//                if (DataLocalManager.getAccount()!= null){
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                }

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
                    String mail = email.getText().toString().trim();
                    String pass = password.getText().toString().trim();

                    if(loginController.login(mail,pass)){
                         if (saveInput){
                                DataLocalManager.setSaveAccount(email.getText().toString(),password.getText().toString());
                         }
                        else{
                            DataLocalManager.setSaveAccount(null,null);
                        }
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
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
//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if(currentAccessToken == null){
//                LoginManager.getInstance().logOut();
//            }
//        }
//    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  accessTokenTracker.startTracking();
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
            Log.d("ZZZ", account.getId());
            customer.setIdCustomer(account.getId());
            customer.setName(account.getDisplayName());
            customer.setAvatar(account.getPhotoUrl()+"");

            DataLocalManager.setAccount(customer);


            //createAccount
            String url = "https://appfooddb.000webhostapp.com/checkAccountAPIUser.php";
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("false")){
                                Log.d("ZZZ",response);
                                Log.d("ZZZ",account.getId()+" "+account.getDisplayName()+" "+account.getPhotoUrl()+"");
                                createAccount(account.getId(),account.getDisplayName(),account.getPhotoUrl()+"");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Nullable
                @org.jetbrains.annotations.Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("idCustomer",account.getId());
                    return params;
                }
            };
            requestQueue.add(stringRequest);


            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AAA", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    public void createAccount(String idCustomer, String name, String img){
        String url = "https://appfooddb.000webhostapp.com/createAccountForAPIUser.php";
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ZZZ",idCustomer+" "+name+" "+img);
                        Log.d("ZZZ", "getAccount "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idCustomer",idCustomer);
                params.put("nameCustomer",name);
                params.put("imgCustomer",img);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void checkIdCustomer(String id){
        String url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            if(response.trim().equals("false")){
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    public void getAllAccount(){
        String url = "https://appfooddb.000webhostapp.com/getAllAccount.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                accounts.put(object.getString("email"),object.getString("password"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
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