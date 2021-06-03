package com.example.project_cnpm.HomePage;

import android.content.Intent;
import android.graphics.Color;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Controller.ILoginController;
import com.example.project_cnpm.Controller.LoginController;
import com.example.project_cnpm.Login.LoginActivity;
import com.example.project_cnpm.Login.LoginDAO;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.R;
//import com.example.project_cnpm.SignUp.SignUpActivity;
import com.example.project_cnpm.Model.Customer;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment implements ILoginView {
    //    private LottieAnimationView lottieAnimationView;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

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

      //  loginController = new LoginController(this,new LoginDAO());

        mapping(view);

        btnChangeSignUp = view.findViewById(R.id.changeSignUp);
        btnBack = view.findViewById(R.id.btnBack_hompage);

        btnChangeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(getActivity(), SignUpActivity.class));
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
                    String mail = email.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    Log.d("CCC",mail+"-----"+pass);
                    MD5 md5 = new MD5();
                    login(mail,md5.enryptPassword(pass));
                    //getAccount(mail,md5.enryptPassword(pass));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
    public void mapping(View view){
        email = view.findViewById(R.id.login_username_w);
        password = view.findViewById(R.id.login_password_w);
        btnSave = view.findViewById(R.id.login_save_password);
        btnLogin = view.findViewById(R.id.login_btn_login_fg);
        notify = view.findViewById(R.id.notify_fail_login);

    }
    // xử lí sự kiện đăng nhập truyền thống
    public void login(String email, String password){
        String url = "https://appfooddb.000webhostapp.com/checkLogin.php";
//        if (username == null || password == null || email == "" || password == ""){
//            notify.setText("*Vui lòng nhập thông tin!");
//        }
//        else{
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("successful")){
                                // get account of user
                                Log.d("CCC",response);
                                getAccount(email,password);
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
                            Log.d("CCC",error.toString());
                        }
                    }){
                @Nullable
                @org.jetbrains.annotations.Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                        params.put("email",email);
                        params.put("password",password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

//    }
    // lấy account từ username, pass
    public void getAccount(String email, String password){
        Customer account = new Customer();
        String url="http://appfooddb.000webhostapp.com/getAccount.php";
//        if(username == null || password == null){
//            return;
//        }
//        else{
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONObject object = new JSONObject(response);
                               String idCustomer = object.getString("idCustomer");
                               String name = object.getString("fullName");
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
                               account.setPhone(phone);
                               account.setDateCreated(date);
                               account.setAddress(address);
                               account.setName(name);
                               account.setAvatar(imgCustomer);
                               account.setUser(new User(email,passwords,status));
                          //     MainActivity.account = account;

                               Log.d("CCC",account.toString());
                               Log.d("CCC",response.length()+"");

//                               if (MainActivity.account != null){
//
//                                   Intent intent = new Intent(getContext(),MainActivity.class);
//                                   intent.putExtra("account",account);
//
//                                   startActivity(intent);
//                               }
//                               else{
//                                   Log.d("CCC", "account = null");
//                               }

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
                    params.put("email",email);
                    params.put("password",password);
                    return params;
                }
            };
            requestQueue.add(jsonArrayRequest);
        }
//    }
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
            user.setEmail(account.getEmail());
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setName(account.getDisplayName());
            customer.setAvatar(account.getPhotoUrl()+"");
         //   customer.setEmail(account.getEmail()+"");

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