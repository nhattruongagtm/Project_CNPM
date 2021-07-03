package com.example.project_cnpm.DishesPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.project_cnpm.Controller.DetailDishController;
import com.example.project_cnpm.DAO.DishDetailDAO;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.Model.DateTime;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.Image;
import com.example.project_cnpm.Model.Price;
import com.example.project_cnpm.Model.PriceSale;
import com.example.project_cnpm.Model.Size;
import com.example.project_cnpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DetailDishActivity extends AppCompatActivity {
    ImageView btnBack;
    FrameLayout background;
    TextView name,des,price,number,numberImg;
    ImageView img;
    CardView btnPlus,btnSub;
    public Dish dish = new Dish();
    public Handler handler;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dish);

        mapping();

        DetailDishController controller = new DetailDishController(this,new DishDetailDAO(this));

        Intent intent = getIntent();

        if (intent !=null) {
            background.setBackgroundColor(intent.getIntExtra("backgroundColor",0));
        }
        Log.d("NNN",intent.getStringExtra("idDish"));
        String id = intent.getStringExtra("idDish");

        controller.getDishById(id);

        handlerNumber();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void handlerNumber(){
        btnPlus = findViewById(R.id.activity_detail_plus);
        btnSub = findViewById(R.id.activity_detail_sub);
        number = findViewById(R.id.activity_detail_number);

        number.setText("1");

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(number.getText().toString());
                if (num > 1){
                    num--;
                }
                number.setText(num+"");
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(number.getText().toString());
                num++;
                number.setText(num+"");
            }
        });
    }
    public void mapping() {
        btnBack = findViewById(R.id.btnBack_from_detail);
        background = findViewById(R.id.background_detail);
        name = findViewById(R.id.activity_detail_name);
        des = findViewById(R.id.activity_detail_des);
        price = findViewById(R.id.activity_detail_price);
        img = findViewById(R.id.activity_detail_img);
        numberImg = findViewById(R.id.activity_detail_number_img);

    }

    public ImageView getBtnBack() {
        return btnBack;
    }

    public void setBtnBack(ImageView btnBack) {
        this.btnBack = btnBack;
    }

    public FrameLayout getBackground() {
        return background;
    }

    public void setBackground(FrameLayout background) {
        this.background = background;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDes() {
        return des;
    }

    public void setDes(TextView des) {
        this.des = des;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public TextView getNumber() {
        return number;
    }

    public void setNumber(TextView number) {
        this.number = number;
    }

    public TextView getNumberImg() {
        return numberImg;
    }

    public void setNumberImg(TextView numberImg) {
        this.numberImg = numberImg;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public CardView getBtnPlus() {
        return btnPlus;
    }

    public void setBtnPlus(CardView btnPlus) {
        this.btnPlus = btnPlus;
    }

    public CardView getBtnSub() {
        return btnSub;
    }

    public void setBtnSub(CardView btnSub) {
        this.btnSub = btnSub;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}