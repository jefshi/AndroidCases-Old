package com.csp.cases.activity.component.camerademo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Jaeger on 16/2/14.
 * <p>
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
        //        setSupportActionBar(toolbar);
        //        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否显示title
    }

    protected void setStatusBar() {
        // StatusBarUtil.setColor(this, getResources().getColor(R.color.title_color), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    public void replaceFragment(FragmentActivity activity, @IdRes int resId, @NonNull Fragment fragment, String tag) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(resId, fragment, tag);
        ft.commit();
    }

    /**
     * Android点击EditText文本框之外任何地方隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev) && v != null) {
                InputMethodManager imm = (InputMethodManager)
                        this.getSystemService(Activity.INPUT_METHOD_SERVICE);

                if (imm != null && imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (!(v instanceof EditText))
            return false;

        //获取输入框当前的location位置
        int[] leftTop = {0, 0};
        v.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();

        return !(event.getX() > left)
                || !(event.getX() < right)
                || !(event.getY() > top)
                || !(event.getY() < bottom);
    }
}
