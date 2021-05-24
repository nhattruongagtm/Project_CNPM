package com.example.project_cnpm.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.SignUp.SignUpActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

//    private LottieAnimationView lottieAnimationView;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    TextView btnChangeSignUp;
    LinearLayout btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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

//        lottieAnimationView = findViewById(R.id.lottie_login);
//        lottieAnimationView.setAnimation("food-carousel.json");
//        lottieAnimationView.playAnimation();
//        lottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);


        // facebook api
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);

        loginButton.setPublishPermissions("email");
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



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

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
        bundle.putString("fields","gender, name, id, firsy_name, last_name");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
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
}