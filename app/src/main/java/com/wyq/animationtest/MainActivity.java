package com.wyq.animationtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<NewBean> news = new ArrayList<>() ;
    private NewAdapter mNewAdapter;
    private boolean isOpenMenu = true ;
    private ImageView mFabIconNew;
    private FloatingActionButton mRightLowerButton;
    private FloatingActionMenu mRightLowerMenu;
    private ImageView mIvPublish;
    private boolean isMenuOpen  = false;
    private ArrayList<ImageView> mImageViews  = new ArrayList<>();
    private ImageView mIv1;
    private ImageView mIv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        showCustomFloationButton() ;
        showObjectAnim() ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNewsData();
            }
        },5000);

        RecyclerView recyclerView = findViewById(R.id.recycler_view) ;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mNewAdapter = new NewAdapter(R.layout.item_news_layout, news);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(mNewAdapter));
        View empty = LayoutInflater.from(this).inflate(R.layout.empty_layout,null) ;
        mNewAdapter.setEmptyView(empty);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isMenuOpen) {
                    showCloseAnim(80);
                    mIvPublish.setImageResource(R.mipmap.ic_launcher);
                }
//                if (mRightLowerMenu != null && mRightLowerMenu.isOpen()){
//                    mRightLowerMenu.close(true);
//                }
//                if (dy > 0){
//                    mFabIconNew.setVisibility(View.GONE);
//                    mRightLowerButton.setVisibility(View.GONE);
//                }else {
//                    mFabIconNew.setVisibility(View.VISIBLE);
//                    mRightLowerButton.setVisibility(View.VISIBLE);
//                }
            }
        });
    }

    /**
     * 本地实现
     */
    private void showObjectAnim() {
        mIvPublish = findViewById(R.id.img_publish);
        mIv1 = findViewById(R.id.iv_1);
        mIv2 = findViewById(R.id.iv_2);

        mImageViews.add(mIv1) ;
        mImageViews.add(mIv2) ;

        mIvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMenuOpen) {
                    showOpenAnim(80);
                    mIvPublish.setImageResource(R.mipmap.ic_launcher_round);
                }else {
                    showCloseAnim(80);
                    mIvPublish.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
        mIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "icon click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCloseAnim(int angle) {
        //for循环来开始小图标的出现动画
        for (int i = 0; i < mImageViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            double a = -Math.cos(20 * Math.PI / 180 * (i * 2 + 1));
            double b = -Math.sin(20 * Math.PI / 180 * (i * 2 + 1));
            double x = a * DensityUtil.dp2px(this,angle);
            double y = b * DensityUtil.dp2px(this,angle);

            set.playTogether(
                    ObjectAnimator.ofFloat(mImageViews.get(i), "translationX", (float) x, (float) (x * 0.25)),
                    ObjectAnimator.ofFloat(mImageViews.get(i), "translationY", (float) y, (float) (y * 0.25)),
                    ObjectAnimator.ofFloat(mImageViews.get(i), "alpha", 1, 0).setDuration(2000)
            );
//      set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(500);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    mIv1.setVisibility(View.GONE);
                    mIv2.setVisibility(View.GONE);

                    //菜单状态置关闭
                    isMenuOpen = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }


        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mIvPublish, "rotation", 0, 90).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

    }

    private void showOpenAnim(int angle) {
        mIv1.setVisibility(View.VISIBLE);
        mIv2.setVisibility(View.VISIBLE);


        //for循环来开始小图标的出现动画
        for (int i = 0; i < mImageViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            //标题1与x轴负方向角度为20°，标题2为100°，转换为弧度
            double a = -Math.cos(20 * Math.PI / 180 * (i * 2 + 1));
            double b = -Math.sin(20 * Math.PI / 180 * (i * 2 + 1));
            double x = a * DensityUtil.dp2px(this,angle);
            double y = b * DensityUtil.dp2px(this,angle);

            set.playTogether(
                    ObjectAnimator.ofFloat(mImageViews.get(i), "translationX", (float) (x * 0.25), (float) x),
                    ObjectAnimator.ofFloat(mImageViews.get(i), "translationY", (float) (y * 0.25), (float) y)
                    , ObjectAnimator.ofFloat(mImageViews.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    //菜单状态置打开
                    isMenuOpen = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mIvPublish, "rotation", 0, 90).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

    }

    /**
     * 第三方库实现
     */
    private void showCustomFloationButton() {
        mFabIconNew = new ImageView(this);
        mFabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_send));
        mRightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(mFabIconNew)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_share));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_gallery));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_manage));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        mRightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(mRightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view
        mRightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                mFabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mFabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                mFabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mFabIconNew, pvhR);
                animation.start();
            }
        });
        rlIcon1.setEnabled(true);
        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "相机", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNewsData() {
        news.clear();
        for (int i=0 ; i < 20; i++){
            NewBean newBean = new NewBean();
            newBean.text = Math.random() * 10-1 +"";
            news.add(newBean) ;
        }
        mNewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_anim_view) {
            startActivity(new Intent(this,AnimViewActivity.class));
        } else if (id == R.id.nav_anim_drawable) {
            startActivity(new Intent(this,AnimDrawableActivity.class));
        } else if (id == R.id.nav_anim_object) {
            startActivity(new Intent(this,AnimObjectActivity.class));
        } else if (id == R.id.nav_anim_nine) {
            startActivity(new Intent(this,AnimNineActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
