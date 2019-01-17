package com.csp.cases.activity.animation;

import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.csp.cases.R;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.MetricsUtil;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

public class VelocityTrackerActivity extends BaseListActivity {

    private VelocityTracker mVT = VelocityTracker.obtain();

    @Override
    protected void onDestroy() {
        mVT.recycle();
        super.onDestroy();
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        return items;
    }

    @Override
    public void initViewContent() {
        imgItem.setImageResource(R.mipmap.item_card);
        super.initViewContent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVT.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVT.computeCurrentVelocity(1); // 确定速度
                LogCat.e(String.format("MOVE: x 轴速率%s，y 轴速率%s", mVT.getXVelocity(), mVT.getYVelocity()));
                break;
            case MotionEvent.ACTION_UP:
                LogCat.e(String.format("UP: x 轴速率%s，y 轴速率%s", mVT.getXVelocity(), mVT.getYVelocity()));
                break;
        }
        return super.onTouchEvent(event);
    }
}