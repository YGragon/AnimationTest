package com.wyq.animationtest;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

public class NewAdapter extends BaseQuickAdapter<NewBean,BaseViewHolder> {

    public NewAdapter(int layoutResId, @Nullable List<NewBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewBean item) {
        TextView title = helper.getView(R.id.new_title) ;
        final ImageView image = helper.getView(R.id.new_image) ;

        title.setText(item.text);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(image);
            }
        });
    }
}
