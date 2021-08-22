package com.example.project_cnpm.View.Slide;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.project_cnpm.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }
    // Arrays
    public int[] sliders_img = {
            R.drawable.app1,
            R.drawable.app2,
            R.drawable.app3
    };
    public String[] headings = {
            "Welcome to my app","Food here is the best","Thank you for choose my app"
    };
    public String[] contents = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
    };



    @Override
    public int getCount() {
        return sliders_img.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.each_component,container, false);

        for(int i = 0; i < sliders_img.length;i++){
            Log.d("bbb", String.valueOf(sliders_img[i]));
        }

        ImageView icon = view.findViewById(R.id.icon_slide);
        TextView heading = view.findViewById(R.id.heading_slide);
        TextView content = view.findViewById(R.id.content_slide);


        icon.setImageResource(sliders_img[position]);
        heading.setText(headings[position]);
        content.setText(contents[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}
