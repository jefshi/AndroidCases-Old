package com.csp.cases.activity.view.arcmenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.csp.cases.R;

/**
 * Created by csp on 2018/4/2 002.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcTipView extends FrameLayout {


    public ArcTipView(Context context) {
        this(context, null, 0, 0);
    }

    public ArcTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ArcTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcTipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.view_arc_tip, this, true);
    }
}
