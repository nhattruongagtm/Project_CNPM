package com.example.project_cnpm.HomePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Login.LoginActivity;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.SignUp.SignUpActivity;
import com.example.project_cnpm.User.Customer;
import com.example.project_cnpm.User.User;
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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    //    private LottieAnimationView lottieAnimationView;
    private CallbackManager callbackManager;
    private LoginButton loginButton;


    //google api
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 0;
    TextView btnChangeSignUp;
    LinearLayout btnBack;

    // khai báo đăng nhập bầng username và password
    private EditText username, password;
    private Button btnLogin;
    private Switch btnSave;
    private TextView notify;




    public LoginFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_login, container, false);


        mapping(view);

        btnChangeSignUp = view.findViewById(R.id.changeSignUp);
        btnBack = view.findViewById(R.id.btnBack_hompage);

        btnChangeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


        // facebook api
        callbackManager = CallbackManager.Factory.create();
        loginButton = view.findViewById(R.id.login_button);

        loginButton.setPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("AAA","Login successful!");
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
        loginGoogle(view);

        // đăng nhập bằng username, password
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String user = username.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    MD5 md5 = new MD5();
                        getAccount(user,md5.enryptPassword(pass));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });



        return view;
    }
    public void mapping(View view){
        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        btnSave = view.findViewById(R.id.login_save_password);
        btnLogin = view.findViewById(R.id.login_btn_login);
        notify = view.findViewById(R.id.notify_fail_login);

    }
    // xử lí sự kiện đăng nhập truyền thống
    public void login(String username, String password){
        String url = "https://appfooddb.000webhostapp.com/checkLogin.php";
        if (username == null || password == null || username == "" || password == ""){
            notify.setText("*Vui lòng nhập thông tin!");
        }
        else{
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("successful")){
                                // get account of user
                                getAccount(username,password);
                            }
                            else if(response.trim().equals("fail")){
                                notify.setText("*Tài khoản không tồn tại!");
                            }
                            else if(response.trim().equals("wrong password")){
                                notify.setText("*Vui lòng nhập đúng password!");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("EEE",error.toString());
                        }
                    }){
                @Nullable
                @org.jetbrains.annotations.Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                        params.put("username",username);
                        params.put("password",password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

    }
    // lấy account từ username, pass
    public void getAccount(String username, String password){
        Customer account = new Customer();
        String url="http://appfooddb.000webhostapp.com/getAccount.php";
        if(username == null || password == null){
            return;
        }
        else{
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONObject object = new JSONObject(response);
                               String idCustomer = object.getString("idCustomer");
                               String name = object.getString("fullName");
                               String usernames = object.getString("username");
                               String passwords = object.getString("password");
                               int status = object.getInt("status");
                               String phone = object.getString("phone");
                               String email = object.getString("email");
                               String imgCustomer = object.getString("imgCustomer");
                               String address = object.getString("address");
                               String d = object.getString("dateCreated");
                               String[] da = d.split("-");
                               Date date = new Date(Integer.parseInt(da[2]),Integer.parseInt(da[1]),Integer.parseInt(da[0]));

                               account.setIdCustomer(idCustomer);
                               account.setStatus(status);
                               account.setEmail(email);
                               account.setPhone(phone);
                               account.setDateCreated(date);
                               account.setAddress(address);
                               account.setName(name);
                               account.setAvatar(imgCustomer);
                               account.setUser(new User(usernames,passwords,status));
                               MainActivity.account = account;
                               if (MainActivity.account != null){

                                   Intent intent = new Intent(getContext(),MainActivity.class);
                                   intent.putExtra("account",account);

                                   startActivity(intent);
                               }
                               else{
                                   Log.d("CCC", "account = null");
                               }

                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   },
                   new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Log.d("CCC","error: "+error.toString());
                       }
                   }){
                @Nullable
                @org.jetbrains.annotations.Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("username",username);
                    params.put("password",password);
                    return params;
                }
            };
            requestQueue.add(jsonArrayRequest);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        // facebook
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("AAA",object.toString());
                try {
                    String name = object.getString("name");

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
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.startTracking();
    }
    public void loginGoogle(View view){
        signInButton = view.findViewById(R.id.sign_in_button);
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
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }
    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Intent intent = new Intent(getActivity(),MainActivity.class);
            User user = new User();
            user.setUsername(account.getId());
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setName(account.getDisplayName());
            customer.setAvatar(account.getPhotoUrl()+"");
            customer.setEmail(account.getEmail()+"");

            intent.putExtra("account",customer);


            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AAA", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }
}