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

public class DishRecommendAdapter extends RecyclerView.Adapter<DishRecommendAdapter.DishTodayHolder> {
    private Context context;
    private ArrayList<Dish> dishes = new ArrayList<>();

    public DishRecommendAdapter(Context context, ArrayList<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @Override
    public DishTodayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_hompage_item_dish_recommended,parent,false);
        return new DishTodayHolder(view);
    }

    @Override
    public void onBindViewHolder(DishRecommendAdapter.DishTodayHolder holder, int position) {
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

            img = itemView.findViewById(R.id.homepage_dish_img_re);
            name = itemView.findViewById(R.id.homepage_dish_name_re);
            price = itemView.findViewById(R.id.homepage_dish_price_re);
            category = itemView.findViewById(R.id.homepage_dish_category_re);
            like = itemView.findViewById(R.id.homepage_dish_checkbox_re);
        }
    }
}
