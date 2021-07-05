package com.example.project_cnpm.Admin.DishesManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.project_cnpm.R;

import java.util.List;

public class DishAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Dish> dishList;

    public DishAdapter(Context context, int layout, List<Dish> dishList) {
        this.context = context;
        this.layout = layout;
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenMonAn, txtLoai, txtGiaS, txtGiaL;
        CheckBox cbChoose;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTenMonAn = convertView.findViewById(R.id.txt_name_dish);
            holder.txtLoai = convertView.findViewById(R.id.txt_category_dish);
            holder.txtGiaS = convertView.findViewById(R.id.txt_price_dish_s);
            holder.txtGiaL = convertView.findViewById(R.id.txt_price_dish_l);
            holder.cbChoose = convertView.findViewById(R.id.cbChoose);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Dish dish = dishList.get(position);
        holder.txtTenMonAn.setText(dish.getName());
        holder.txtLoai.setText(dish.getCategory());
        holder.txtGiaS.setText(dish.getPriceS() + " VND");
        holder.txtGiaL.setText(dish.getPriceL() + " VND");

        return convertView;

    }
}
