package com.wyq.animationtest;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AnimObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_object);

        final Button btnObjectAnim = findViewById(R.id.btn_animation_object) ;
        Button btnTranslateAnim = findViewById(R.id.btn_translate_y_anim) ;
        final Button btnChangeBgAnim = findViewById(R.id.btn_change_bg__anim) ;
        Button btnSetAnim = findViewById(R.id.btn_set_anim) ;

        btnTranslateAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(btnObjectAnim,"translationY",-btnObjectAnim.getHeight()).start();
            }
        });

        btnChangeBgAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator colorAnim = ObjectAnimator.ofInt(btnObjectAnim,"backgroundColor",0xFFF8080,0xFF8080FF);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
            }
        });

        btnSetAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(btnObjectAnim,"rotationX",0,360),
                        ObjectAnimator.ofFloat(btnObjectAnim,"rotationY",0,180),
                        ObjectAnimator.ofFloat(btnObjectAnim,"rotation",0,90),
                        ObjectAnimator.ofFloat(btnObjectAnim,"translationX",0,90),
                        ObjectAnimator.ofFloat(btnObjectAnim,"translationY",0,90),
                        ObjectAnimator.ofFloat(btnObjectAnim,"scaleX",1,1.5f),
                        ObjectAnimator.ofFloat(btnObjectAnim,"scaleY",1,0.5f),
                        ObjectAnimator.ofFloat(btnObjectAnim,"alpha",1,0.25f,1)
                );
                animatorSet.setDuration(5 * 1000);
                animatorSet.start();
            }
        });

    }
}
