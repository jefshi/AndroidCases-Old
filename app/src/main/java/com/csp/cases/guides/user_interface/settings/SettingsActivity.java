package com.csp.cases.guides.user_interface.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.csp.cases.R;

/**
 * 官方入口
 * https://developer.android.google.cn/guide/topics/ui/settings
 * <p>
 * Android 设置设计指南
 * https://source.android.google.cn/devices/tech/settings/settings-guidelines
 * <p>
 * 首选项组件和属性详细说明
 * https://developer.android.google.cn/guide/topics/ui/settings/components-and-attributes
 * <p>
 * implementation 'com.android.support:preference-v7:28.0.0'
 * <p>
 * R.layout.ac_settings
 * R.layout.fg_setting
 * R.xml.preferences
 * R.style.AppTheme（已经被注释掉了）
 */
public class SettingsActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    @NonNull
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final Bundle args = pref.getExtras();

//        // 原官方 Demo 写法 begin
//        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
//                getClassLoader(),
//                pref.getFragment(),
//                args);
//        fragment.setArguments(args);
//        fragment.setTargetFragment(caller, 0);
//        // 原官方 Demo 写法 end

        final Fragment fragment = Fragment.instantiate(getContext(), pref.getFragment(), args);
        fragment.setTargetFragment(caller, 0);

        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

}
