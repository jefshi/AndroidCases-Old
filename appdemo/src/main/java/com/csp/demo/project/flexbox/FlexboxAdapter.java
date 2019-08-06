package com.csp.demo.project.flexbox;


import android.content.Context;
import android.view.View;

import com.csp.adapter.recyclerview.SingleAdapter;
import com.csp.adapter.recyclerview.ViewHolder;
import com.csp.demo.R;

public class FlexboxAdapter extends SingleAdapter<String> {

    public FlexboxAdapter(Context context) {
        super(context, R.layout.item_text);
    }

    @Override
    protected void onBind(ViewHolder holder, final String datum, int position) {
        holder.setText(R.id.txt, datum);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(datum);
                notifyDataSetChanged();
            }
        });
    }
}
