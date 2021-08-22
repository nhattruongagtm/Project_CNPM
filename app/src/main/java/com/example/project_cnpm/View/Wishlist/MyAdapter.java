package com.example.project_cnpm.View.Wishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_cnpm.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Wishlist> products;

    public MyAdapter(Context context, ArrayList<Wishlist> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.wishilist_row,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Wishlist w = products.get(position);
        holder.imgProduct.setImageResource(w.getImg());
        holder.nameProduct.setText(w.getName());
        holder.priceProduct.setText(w.getPrice()+"");
        holder.quantityProduct.setText(w.getQuantity()+"");
        holder.desProduct.setText(w.getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    // khởi tạo ViewHolder để khai báo các thành phần giao diện //trên hàng
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView nameProduct, desProduct, priceProduct, quantityProduct;
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            imgProduct = v.findViewById(R.id.imgProduct);
            nameProduct = v.findViewById(R.id.nameProduct);
            desProduct = v.findViewById(R.id.desProduct);
            priceProduct = v.findViewById(R.id.priceProduct);
            quantityProduct = v.findViewById(R.id.quantityProduct);

        }
    }

}
