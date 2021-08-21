package com.example.project_cnpm.DishesPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_cnpm.Controller.DishDetailController;
import com.example.project_cnpm.DAO.DishDetailDAO;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.R;

import java.util.ArrayList;


public class DishDetailView extends AppCompatActivity {
    ImageView btnBack;
    FrameLayout background;
    TextView name,des,price,number,numberImg,priceSale;
    ImageView img;
    CardView btnPlus,btnSub;
    public Dish dish = new Dish();
    public Handler handler;
    private LinearLayout sizes;
    private TextView nameSize;
    private CardView sizeItem;


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

        DishDetailController controller = new DishDetailController(this,new DishDetailDAO(this));

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
        priceSale = findViewById(R.id.activity_detail_price_sale);
        img = findViewById(R.id.activity_detail_img);
        numberImg = findViewById(R.id.activity_detail_number_img);
        sizes = findViewById(R.id.activity_dish_detail_sizes);


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

    public void showDish(Dish dish){
        name.setText(dish.getName());
        des.setText(dish.getDescribe());

        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<String> nameSizes = new ArrayList<>();

        for(int i = 0; i < dish.getPriceSale().size();i++){
            int priceDish = 0;
            int percent = dish.getPriceSale().get(i).getPriceSale();
            int pr = dish.getPrice().get(i).getPrice();
            if(percent > 0){
                priceDish = pr - (pr * percent/100);
            }
            else {
                priceDish = pr;
            }
            prices.add(priceDish);
            nameSizes.add(dish.getSize().get(dish.getPriceSale().size()-i-1).getIdSize().toUpperCase());
        }

        int percent = dish.getPriceSale().get(0).getPriceSale();

        if(percent > 0){
            priceSale.setText(dish.getPrice().get(0).getPrice()+"");
        }
        else{
            priceSale.setText("");
        }


        for (int i = 0; i < dish.getPrice().size();i++){

            LinearLayout itemView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_detail_dish_size_item,null);

            nameSize = itemView.findViewById(R.id.activity_dish_detail_name_size);
            sizes.addView(itemView);
            nameSize.setText(nameSizes.get(i)+"");
            nameSize.setTextColor(Color.WHITE);

            LinearLayout itemSize = (LinearLayout) sizes.getChildAt(0);
            CardView size = itemSize.findViewById(R.id.activity_detail_item);
            size.setCardBackgroundColor(Color.parseColor("#FFC107"));

            int p = prices.get(i);
            int pos = i;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   price.setText(p +" VNĐ");
                   for(int i = 0; i < nameSizes.size();i++){
                       LinearLayout itemSize = (LinearLayout) sizes.getChildAt(i);
                       if(i == pos){
                           CardView size = itemSize.findViewById(R.id.activity_detail_item);
                           size.setCardBackgroundColor(Color.parseColor("#FFC107"));
                       }
                       else{
                           CardView size = itemSize.findViewById(R.id.activity_detail_item);
                           size.setCardBackgroundColor(Color.parseColor("#FFF5D7"));
                       }
                   }

                    int ps = 0;

                        if(dish.getPrice().get(pos).getPrice() != prices.get(pos)){
                            Log.d("GGG",prices.get(pos)+"");
                            Log.d("GGG",prices.get(pos)+"");

                            ps = dish.getPrice().get(pos).getPrice();
                        }
                        else{
                            ps = 0;
                        }
                        if (ps != 0){
                            priceSale.setText(ps+"");
                        }
                        else{
                            priceSale.setText("");
                        }
                }
            });


        }
        price.setText(prices.get(0)+" VNĐ");

        numberImg.setText(dish.getImg().size()+"");
        Glide.with(this).load(dish.getImg().get(0).getLinkImage()).into(img);
    }
}