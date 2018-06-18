package com.wyq.animationtest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class AnimDrawableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_drawable);

        ImageView animDrawable = findViewById(R.id.anim_drawable_iv) ;
        animDrawable.setBackgroundResource(R.drawable.anim_drawable);
        AnimationDrawable animationDrawable = (AnimationDrawable) animDrawable.getBackground();
        animationDrawable.start();
    }
}
