package com.example.project_cnpm.Slide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_cnpm.R;

public class SlideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private SliderAdapter sliderAdapter;

    private TextView[] dots;

    private Button btnNext, btnBack;

    private int currentPage = 0;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(viewListener);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage - 1);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage + 1);
            }
        });


    }
    public void addDots(int position){
        dots = new TextView[3];
        //xóa các phần tử đã thêm trước đó
        linearLayout.removeAllViews();
        for (int i = 0; i < dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(50);
            dots[i].setTextColor(getResources().getColor(R.color.transparency));

            linearLayout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }
    // dấu 3 chấm
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


            addDots(position);

            currentPage = position;

            if(position ==0){
                btnBack.setEnabled(false);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.INVISIBLE);

                btnNext.setText("Next");
                btnBack.setText("");
            }
            else if (position == dots.length - 1){
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Finish");
                count++;
                btnBack.setText("Back");
            }
            else{
                btnBack.setEnabled(true);
                btnNext.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("Next");
                btnBack.setText("Back");
            }
            if (count != 0){
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("AAA", "Click to MainActivity" + count);
                      //  startActivity(new Intent(SlideActivity.this, LoginActivity.class));
                        count = 0;
                    }
                });

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}