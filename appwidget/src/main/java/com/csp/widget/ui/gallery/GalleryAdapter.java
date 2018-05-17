package com.csp.widget.ui.gallery;


import android.content.Context;
import android.widget.ImageView;

import com.csp.widget.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * GalleryAdapter
 * Created by chenshp on 2018/4/13.
 */
public class GalleryAdapter extends CommonAdapter<Integer> {

    public GalleryAdapter(Context context) {
        super(context, R.layout.item_gallery, new ArrayList<Integer>());
    }

    public void addData(List<Integer> games, boolean append) {
        if (!append)
            mDatas.clear();

        mDatas.addAll(games);
    }

    public Integer getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    protected void convert(ViewHolder holder, Integer integer, int position) {
//        Context context = holder.getConvertView().getContext();
        ImageView mImgBoostGame = holder.getView(R.id.img_boost_game);
        mImgBoostGame.setImageResource(integer);

//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.shape_bg_icon)
//                .error(R.drawable.shape_bg_icon)
//                .priority(Priority.HIGH);
//
//        Glide.with(context)
//                .load(game.getHomeImg())
//                .apply(options)
//                .into(mImgBoostGame);
    }
}
