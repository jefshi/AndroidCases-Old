package com.csp.cases.zoom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.csp.adapter.recyclerview.ISnapHelperExtend;
import com.csp.adapter.recyclerview.ItemViewHolder;
import com.csp.adapter.recyclerview.SingleAdapter;
import com.csp.cases.R;
import com.csp.cases.base.activity.BaseActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class ZoomImageActivity extends BaseActivity {

    private RecyclerView rcv;
    private ImageVpAdapter adapter;
    //图片所在的位置
    private int position;


    public static void start(Context context) {
        Intent starter = new Intent(context, ZoomImageActivity.class);
        context.startActivity(starter);
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        return null;
    }

    @Override
    public void initView() {
        rcv = findViewById(R.id.rcv);

        adapter = new ImageVpAdapter(this);
        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcv.setAdapter(adapter);


        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rcv);
        ISnapHelperExtend.OnScrollListener listener = new ISnapHelperExtend.OnScrollListener(snapHelper);
//        listener.setOnPageChangeListener(new ISnapHelperExtend.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                mLastPosition = position;
//                refreshIndicator();
//            }
//        });
        rcv.addOnScrollListener(listener);

        //加载数据
        loadData();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.ac_recycler);
    }


    @Override
    public void initViewContent() {
    }

    private List<String> list;

    /**
     * 加载数据
     */
    private void loadData() {
        list = new ArrayList<>();
        list.clear();
        list.add("http://scimg.jb51.net/allimg/151228/14-15122Q60431W4.jpg");
        list.add("http://img1.3lian.com/2015/a1/137/d/37.jpg");
        list.add("http://pic.qiantucdn.com/58pic/18/37/96/18n58PICPb7_1024.jpg");
        list.add("http://pic.qiantucdn.com/58pic/12/81/76/44n58PICAT2.jpg");
        list.add("http://pic.qiantucdn.com/58pic/14/44/24/94b58PICCxn_1024.jpg");
//        tvTotal.setText(list.size()+"");
//        vpImgCheck.setCurrentItem(position);
        adapter.addData(list, false);
        adapter.notifyDataSetChanged();
    }

    /**
     * 适配器
     */
    private class ImageVpAdapter extends SingleAdapter<String> {

        public ImageVpAdapter(Context context) {
            super(context, R.layout.ac_image);
        }

        @Override
        public void onBind(ItemViewHolder holder, String datum, int position) {
            ScaleView img = holder.getView(R.id.img_zoom);
            img.setImageResource(R.mipmap.ic_launcher);
            img.setScaleType(ImageView.ScaleType.MATRIX);
            // img.setOnTouchListener(new MulitPointTouchListener(img));
        }
    }
}
