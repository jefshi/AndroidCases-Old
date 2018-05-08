package com.csp.library.android.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.csp.library.android.interfaces.InitialUi;
import com.csp.utils.android.log.LogCat;

import java.util.List;

/**
 * Description: 用于 FragmentActivity 继承
 * <p>Create Date: 2016-12-15
 * <p>Modify Date: 2016-06-13
 *
 * @author csp
 * @version 1.0.2
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BaseLibraryFragmentActivity extends FragmentActivity implements InitialUi, BaseLibraryFuns {
    private int containerViewId;
    private FragmentInfo[] fragments;
    private Fragment lastFrg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        // 初始化UI
        initialUi();
    }

    @Override
    public void initialUi() {
        initBundle();
        initView();
        initViewContent();
        initViewEvent();
    }

    /**
     * @link Activity.setContentView()
     */
    protected abstract void setContentView();

    // ========================================
    // Fragment
    // ========================================

    /**
     * 设置[Fragment]列表
     *
     * @param fragments       [Fragment]列表
     * @param containerViewId [Fragment]容器资源ID
     */
    protected void setFragments(List<FragmentInfo> fragments, int containerViewId) {
        FragmentInfo[] frgs = null;
        if (!isEmpty(fragments)) {
            frgs = new FragmentInfo[fragments.size()];
            for (int i = 0; i < frgs.length; i++) {
                frgs[i] = fragments.get(i);
            }
        }
        setFragments(frgs, containerViewId);
    }

    /**
     * 设置[Fragment]列表
     *
     * @param fragments       [Fragment]列表
     * @param containerViewId [Fragment]容器资源ID
     */
    protected void setFragments(FragmentInfo[] fragments, int containerViewId) {
        this.fragments = fragments;
        this.containerViewId = containerViewId;
    }

    /**
     * 显示选择的[Fragment]
     *
     * @param num 需要显示的[Fragment]所在列表序号
     */
    protected void showFragment(int num) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // 关闭旧的[Fragment]显示
        if (lastFrg != null) {
            ft.detach(lastFrg);
        }

        // 显示新的[Fragment]
        String frgTag = fragments[num].getTag();
        Fragment frg = fm.findFragmentByTag(frgTag);
        if (frg == null) {
            frg = fragments[num].getFragment();
            ft.add(containerViewId, frg, frgTag);
        } else {
            ft.attach(frg);
        }
        ft.commit();

        lastFrg = frg;
    }

    /**
     * Fragment 信息
     */
    @SuppressWarnings("WeakerAccess")
    public static class FragmentInfo {
        private String tag;
        private Fragment fragment;

        public FragmentInfo(String tag, Fragment fragment) {
            this.tag = tag;
            this.fragment = fragment;
        }

        public String getTag() {
            return tag;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }

    // ========================================
    // BaseLibraryFuns
    // ========================================
    @Override
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

    @Override
    public View getView() {
        return getWindow().getDecorView();
    }

    @Override
    public boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Override
    public boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    @Override
    public boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}
