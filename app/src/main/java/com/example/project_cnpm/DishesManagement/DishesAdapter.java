package com.example.project_cnpm.DishesManagement;

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

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesHolder>  {
    private Context context;
    private ArrayList<DishManagement> dishes = new ArrayList<>();

    public DishesAdapter(Context context, ArrayList<DishManagement> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @Override
    public DishesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_dish_management,parent,false);
        return new DishesHolder(view);
    }

    @Override
    public void onBindViewHolder(DishesAdapter.DishesHolder holder, int position) {
        DishManagement dishManagement = dishes.get(position);
        if (dishManagement == null){
            return;
        }
        holder.name.setText(dishManagement.getName());
        holder.price.setText(dishManagement.getPrice()+" VNĐ");
        holder.choose.setChecked(false);
        holder.category.setText(dishManagement.getCategory());
        holder.img.setImageResource(dishManagement.getImg());
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class DishesHolder extends RecyclerView.ViewHolder
    {
        TextView name, category, price;
        CheckBox choose;
        ImageView img;

        public DishesHolder( View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_dish_management);
            name = itemView.findViewById(R.id.name_dish_management);
            price = itemView.findViewById(R.id.price_dish_management);
            category = itemView.findViewById(R.id.category_dish_management);
            choose = itemView.findViewById(R.id.checkBox_dish_management);
        }
    }
}