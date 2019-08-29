package com.csp.cases.guides.user_interface.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceDataStore;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.csp.cases.CaseApp;
import com.csp.cases.MainActivity;
import com.csp.cases.R;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.log.LogCat;

public class SettingsFragment extends PreferenceFragmentCompat implements
        Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Context getContext() {
        Context context = super.getContext();
        if (context != null)
            return context;

        context = getView() == null ? null : getView().getContext();
        if (context != null)
            return context;

        if (mContext != null)
            return mContext;

        return CaseApp.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTheme(R.style.AppTheme); // TODO 不推荐采用这种方式
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

//        // 动态更新摘要 begin
//        EditTextPreference countingPreference = (EditTextPreference) findPreference("counting");
//        countingPreference.setSummaryProvider(EditTextPreference.SimpleSummaryProvider.getInstance());
//
//        countingPreference.setSummaryProvider(new SummaryProvider<EditTextPreference>() {
//            @Override
//            public CharSequence provideSummary(EditTextPreference preference) {
//                String text = preference.getText();
//                if (TextUtils.isEmpty(text)){
//                    return "Not set";
//                }
//                return "Length of saved value: " + text.length();
//            }
//        });
//        // 动态更新摘要 end

        // 首选项操作
        EditTextPreference editTextPreference = (EditTextPreference) findPreference("signature");
        editTextPreference.setVisible(true);
        editTextPreference.setOnPreferenceChangeListener(this);

        Intent intent = new Intent(getContext(), MainActivity.class);
        findPreference("activitybyjava").setIntent(intent);

        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        findPreference("webpagebyjava").setIntent(intent);

        findPreference("signature_click").setOnPreferenceClickListener(this);
        findPreference("signature_click_02").setOnPreferenceClickListener(this);
        findPreference("counting_click").setOnPreferenceClickListener(this);
        findPreference("counting_click_02").setOnPreferenceClickListener(this);

        // 读取首选项值
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);

        String signature = sharedPreferences.getString("signature", "");
        LogCat.e("通过 SharedPreferences 获取：" + signature);

        // 使用自定义数据存储
        sharedPreferences = getPreferenceManager().getSharedPreferences();
        LogCat.e("设置了 PreferenceDataStore 后，getSharedPreferences() 就变为：" + sharedPreferences);

        DataStore dataStore = new DataStore();
        findPreference("counting").setPreferenceDataStore(dataStore);
        getPreferenceManager().setPreferenceDataStore(dataStore);

        PreferenceDataStore pDataStore = findPreference("counting").getPreferenceDataStore();
        LogCat.e("通过 SharedPreferences 获取：" + pDataStore.getString("counting", null));
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (sharedPreferences != null)
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (sharedPreferences != null)
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        // TODO 通过 SharedPreferences 或 PreferenceDataStore 设置值后，Preference 不会刷新
        // TODO Preference 值改变后，无法显示再 Preference 的 summary（或其他地方）上
        SharedPreferences sp;
        PreferenceDataStore pDataStore;
        String key = preference.getKey();
        switch (key) {
            case "signature_click":
                sp = PreferenceManager.getDefaultSharedPreferences(getContext());

                String tip = sp != null
                        ? "存在 SharedPreferences：" + sp.getString("signature", null)
                        : "SharedPreferences 为 NULL";
                ToastUtil.showToast(tip);
                break;
            case "signature_click_02":
                sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                if (sp == null) {
                    ToastUtil.showToast("SharedPreferences 为 NULL");
                    break;
                }

                SharedPreferences.Editor edit = sp.edit();
                edit.putString("signature", "通过 SharedPreferences 设置值");
                edit.apply();

                ToastUtil.showToast("注意 LogCat：registerOnSharedPreferenceChangeListener");
                break;
            case "counting_click":
                pDataStore = findPreference("counting").getPreferenceDataStore();
                String counting = pDataStore == null ? "不存在 PreferenceDataStore"
                        : "存在 PreferenceDataStore：" + pDataStore.getString("counting", null);
                ToastUtil.showToast(counting);
                break;
            case "counting_click_02":
                pDataStore = findPreference("counting").getPreferenceDataStore();
                if (pDataStore == null)
                    ToastUtil.showToast("PreferenceDataStore 为 NULL");
                else
                    pDataStore.putString("counting", "通过 PreferenceDataStore 设置值");
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * 执行 OnPreferenceChangeListener 使您能够侦听Preference 的值将在何时出现变动
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        LogCat.e("setOnPreferenceChangeListener value: " + newValue);
        return true;
    }

    /**
     * 在使用 SharedPreferences 保留 Preference 值时，您还可以使用 SharedPreferences.OnSharedPreferenceChangeListener 侦听变动
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        LogCat.e("registerOnSharedPreferenceChangeListener value: " + sp.getString(key, ""));
    }
}
