package com.example.project_cnpm.Utility;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project_cnpm.R;

public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Common.isConnectedToInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);

            AppCompatButton btnTry = layout_dialog.findViewById(R.id.try_connect_internet);


            //hiển thị dialog
            AlertDialog dialog = builder.create();

            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            LottieAnimationView lottieAnimationView = layout_dialog.findViewById(R.id.lottie_disconnected_internet);
            lottieAnimationView.setAnimation("disconnected.json");
            lottieAnimationView.playAnimation();
            lottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);

            btnTry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });

        }
    }
}
