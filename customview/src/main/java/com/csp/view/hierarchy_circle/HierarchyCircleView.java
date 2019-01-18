package com.csp.view.hierarchy_circle;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class HierarchyCircleView extends ViewGroup {

    private Context mContext;


    public HierarchyCircleView(Context context) {
        this(context, null, 0);
    }

    public HierarchyCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HierarchyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HierarchyCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    /**
     * 缓存复用
     */
    private final LinkedList<ImageView> imageViews = new LinkedList<>();
    ;

    private ImageView get() {



        ImageView imageView = new ImageView(mContext);

        return imageView;
    }
}
