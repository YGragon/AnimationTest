package com.wyq.animationtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AnimObjectActivity extends AppCompatActivity {

    private Button mBtnObjectAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_object);

        mBtnObjectAnim = findViewById(R.id.btn_animation_object);
        Button btnTranslateAnim = findViewById(R.id.btn_translate_y_anim) ;
        final Button btnChangeBgAnim = findViewById(R.id.btn_change_bg__anim) ;
        Button btnSetAnim = findViewById(R.id.btn_set_anim) ;
        Button btnChangeBtnWidthAnim = findViewById(R.id.btn_change_btn_width_anim) ;

        btnTranslateAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(mBtnObjectAnim,"translationY",-mBtnObjectAnim.getHeight()).start();
            }
        });

        btnChangeBgAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator colorAnim = ObjectAnimator.ofInt(mBtnObjectAnim,"backgroundColor",0xFFF8080,0xFF8080FF);
                colorAnim.setDuration(3000);
                colorAnim.setEvaluator(new ArgbEvaluator());
                colorAnim.setRepeatCount(ValueAnimator.INFINITE);
                colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                colorAnim.start();
                colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        long duration = animation.getDuration();
                        Log.e("Anim", "onAnimationUpdate: duration----->"+duration );
                    }
                });
            }
        });

        btnSetAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"rotationX",0,360),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"rotationY",0,180),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"rotation",0,90),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"translationX",0,90),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"translationY",0,90),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"scaleX",1,1.5f),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"scaleY",1,0.5f),
                        ObjectAnimator.ofFloat(mBtnObjectAnim,"alpha",1,0.25f,1)
                );
                animatorSet.setDuration(5 * 1000);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                    }

                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                    }
                });
            }
        });
        btnChangeBtnWidthAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 会闪烁
//                viewWrapperAnim() ;
                // 完美实现
                valueAnim(mBtnObjectAnim.getWidth(),500) ;
            }
        });

    }

    private void valueAnim(final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            // 持有一个 IntEvaluator 对象方便下面估值的时候使用
            IntEvaluator intEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentVallue = (int) animation.getAnimatedValue();

                float fraction = animation.getAnimatedFraction();
                mBtnObjectAnim.getLayoutParams().width = intEvaluator.evaluate(fraction,start,end) ;
                mBtnObjectAnim.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }

    private void viewWrapperAnim() {
        ViewWrapper viewWrapper = new ViewWrapper(mBtnObjectAnim);
        ObjectAnimator.ofInt(viewWrapper,"width",500).setDuration(5000).start();
    }

    private static class ViewWrapper{
        private View mTarget ;
        public ViewWrapper(View target){
            this.mTarget = target ;
        }

        public int getWidth(){
            return mTarget.getLayoutParams().width ;
        }

        public void setWidth(int width){
            mTarget.getLayoutParams().width = width ;
            mTarget.requestLayout();
        }
    }
}
