package com.example.project_cnpm.DishesPage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_cnpm.HomePage.Dish;
import com.example.project_cnpm.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DishesPageApdater extends RecyclerView.Adapter<DishesPageApdater.DishesPageHolder>{
    private Context context;
    private ArrayList<DishPageModel> dishes = new ArrayList<>();

    public DishesPageApdater(Context context, ArrayList<DishPageModel> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @NonNull
    @NotNull
    @Override
    public DishesPageHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dishes_page_item,parent,false);

        return new DishesPageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DishesPageHolder holder, int position) {
        DishPageModel dish = dishes.get(position);
        if (dish == null){
            return;
        }
        holder.cb.setChecked(false);
        holder.background.setCardBackgroundColor(dish.getBackground());
        holder.name.setText(dish.getName());
        holder.price.setText(dish.getPrice()+ " VNĐ");
        holder.img.setImageResource(dish.getImg());


        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aaa",position+"");
                Intent intent = new Intent(context,DetailDishActivity.class);
                intent.putExtra("backgroundColor",dish.getBackground());
                Log.d("aaa",dish.getBackground()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class DishesPageHolder extends RecyclerView.ViewHolder{

        private CardView background;
        private TextView name, price;
        private CheckBox cb;
        private ImageView img;

        public DishesPageHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.dishes_card);
            name = itemView.findViewById(R.id.dishes_name);
            price = itemView.findViewById(R.id.dishes_price);
            cb = itemView.findViewById(R.id.dishes_checkbox);
            img = itemView.findViewById(R.id.dishes_img);

        }
    }
}
