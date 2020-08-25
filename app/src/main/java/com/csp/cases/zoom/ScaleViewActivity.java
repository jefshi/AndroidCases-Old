package com.csp.cases.zoom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.csp.cases.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dj
 * @version 1.0
 * @description 缩放图片
 * @created on 2017/4/19.
 */

public class ScaleViewActivity extends AppCompatActivity {
    private List<String> list;
    //    private RelativeLayout rlBack;
//    private TextView tvCurrent;
//    private TextView tvTotal;
    private ViewPager vpImgCheck;
    private ImageVpAdapter adapter;
    //图片所在的位置
    private int position;

    public static void start(Context context) {
        Intent starter = new Intent(context, ScaleViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_vepa);
        initView();
        list = new ArrayList<>();
        adapter = new ImageVpAdapter();
        position = 0;
        vpImgCheck.setAdapter(adapter);
//        vpImgCheck.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                int current=i+1;
//                tvCurrent.setText(current+"");
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
        //加载数据
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        list.clear();
        list.add("http://scimg.jb51.net/allimg/151228/14-15122Q60431W4.jpg");
        list.add("http://img1.3lian.com/2015/a1/137/d/37.jpg");
        list.add("http://pic.qiantucdn.com/58pic/18/37/96/18n58PICPb7_1024.jpg");
        list.add("http://pic.qiantucdn.com/58pic/12/81/76/44n58PICAT2.jpg");
        list.add("http://pic.qiantucdn.com/58pic/14/44/24/94b58PICCxn_1024.jpg");
//        tvTotal.setText(list.size()+"");
        vpImgCheck.setCurrentItem(position);
        adapter.notifyDataSetChanged();
    }


    private void initView() {
//        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
//        tvCurrent = (TextView) findViewById(R.id.tv_current);
//        tvTotal = (TextView) findViewById(R.id.tv_total);
        vpImgCheck = (ViewPager) findViewById(R.id.vpg);
    }

    /**
     * 适配器
     */
    private class ImageVpAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ScaleView scaleView = new ScaleView(ScaleViewActivity.this);
            scaleView.setScaleType(ImageView.ScaleType.MATRIX);
//            Picasso.with(ScaleViewActivity.this).load(list.get(position)).placeholder(R.mipmap.load).error(R.mipmap.error)
//                    .resize(500,500).centerCrop().into(scaleView);

            scaleView.setImageResource(R.mipmap.ic_launcher);
            container.addView(scaleView);
            return scaleView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof ScaleView) {
                ScaleView view = (ScaleView) object;
                container.removeView(view);
            }
        }
    }
}