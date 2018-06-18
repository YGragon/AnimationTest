package com.wyq.animationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 视图动画例子
 */
public class AnimViewActivity extends AppCompatActivity {

    private ImageView mViewAnimShowIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_view);
        // mViewAnimShowIcon 不能是final
        mViewAnimShowIcon = findViewById(R.id.iv_view_anim_show_icon);
        Button alpha = findViewById(R.id.btn_view_show_alpha);
        Button rotate = findViewById(R.id.btn_view_show_rotate);
        Button scale = findViewById(R.id.btn_view_show_scale);
        Button translate = findViewById(R.id.btn_view_show_translate);

        // (1) 平移
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 必须先清除动画
                mViewAnimShowIcon.clearAnimation();
                Animation translateAnim = AnimationUtils.loadAnimation(AnimViewActivity.this, R.anim.anim_translate);
                mViewAnimShowIcon.setAnimation(translateAnim);
            }
        });

        // (2) 缩小
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewAnimShowIcon.clearAnimation();
                Animation scaleAnim = AnimationUtils.loadAnimation(AnimViewActivity.this, R.anim.anim_scale);
                mViewAnimShowIcon.setAnimation(scaleAnim);
            }
        });

        // (3) 旋转
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewAnimShowIcon.clearAnimation();
                Animation rotateAnim = AnimationUtils.loadAnimation(AnimViewActivity.this, R.anim.anim_rotate);
                mViewAnimShowIcon.setAnimation(rotateAnim);
            }
        });

        // (4) 透明度
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewAnimShowIcon.clearAnimation();
                Animation alphaAnim = AnimationUtils.loadAnimation(AnimViewActivity.this, R.anim.anim_alpha);
                mViewAnimShowIcon.setAnimation(alphaAnim);
            }
        });
    }
}
