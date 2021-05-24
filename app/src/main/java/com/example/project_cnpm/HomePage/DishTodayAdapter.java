package com.example.project_cnpm.HomePage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_cnpm.R;

import java.util.ArrayList;

public class DishTodayAdapter extends RecyclerView.Adapter<DishTodayAdapter.DishTodayHolder> {
    private Context context;
    private ArrayList<Dish> dishes = new ArrayList<>();

    public DishTodayAdapter(Context context, ArrayList<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @Override
    public DishTodayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_hompage_item_dish_today,parent,false);
        return new DishTodayHolder(view);
    }

    @Override
    public void onBindViewHolder(DishTodayAdapter.DishTodayHolder holder, int position) {
        Dish dish = dishes.get(position);
        if (dish == null){
            return;
        }
        holder.name.setText(dish.getName());
        holder.price.setText(dish.getPrice()+" VNĐ");
        holder.like.setChecked(false);
        holder.category.setText(dish.getCategory());
        holder.img.setImageResource(dish.getImg());
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class DishTodayHolder extends RecyclerView.ViewHolder
    {
        TextView name, category, price;
        CheckBox like;
        ImageView img;

        public DishTodayHolder( View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_dish_today_homepage);
            name = itemView.findViewById(R.id.homepage_name_today);
            price = itemView.findViewById(R.id.homepage_price_today);
            category = itemView.findViewById(R.id.hompage_category_today);
            like = itemView.findViewById(R.id.homepage_checkbox_today);
        }
    }
}
