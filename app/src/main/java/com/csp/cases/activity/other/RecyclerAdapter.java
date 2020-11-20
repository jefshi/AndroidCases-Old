package com.csp.cases.activity.other;

import android.content.Context;

import com.csp.adapter.recyclerview.ItemViewHolder;
import com.csp.adapter.recyclerview.SingleAdapter;
import com.csp.cases.R;
import com.csp.utils.android.log.LogCat;


public class RecyclerAdapter extends SingleAdapter<String> {

    public RecyclerAdapter(Context context) {
        super(context, R.layout.item_img);
    }

    @Override
    public void onBind(ItemViewHolder holder, String datum, int position) {
    }
}
