package com.csp.cases.guides.user_interface.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.AndroidResources;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csp.cases.R;

/**
 * Build a hierarchy in code
 * https://developer.android.google.cn/guide/topics/ui/settings/programmatic-hierarchy
 */
public class SettingPartFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTheme(R.style.YourTheme);  // TODO 不推荐采用这种方式
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle paramBundle) {
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.YourTheme);
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
//        View view = localInflater.inflate(R.layout.simple_tabs, container, false);
//
//
//        super.onCreateView(inflater, container, paramBundle);
//
//        View view = inflater.inflate(R.layout.fg_setting, container, false);
//        mRecyclerView = view.findViewById(R.id.rcv);
//        addPreferencesFromResource(R.xml.preferences_part);
//        int androidRListContainer = AndroidResources.ANDROID_R_LIST_CONTAINER;
////        mRecyclerView =  txt.setText("成功");
//
////        addPreferencesFromResource(R.xml.preferences_part);
////        ButterKnife.bind(this, view);
////        this.init();
//        return view;
//    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.preferences_part);
//    }

//
//    RecyclerView mRecyclerView;
//
//    @Override
//    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
//        return mRecyclerView;
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        TextView txt = view.findViewById(R.id.txt_test);
//        mRecyclerView =
//        txt.setText("成功");
//    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_part);

//        addPreferencesFromResource(R.xml.preferences_part);
//        Context context = getPreferenceManager().getContext();
//        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
//
//        SwitchPreferenceCompat notificationPreference = new SwitchPreferenceCompat(context);
//        notificationPreference.setKey("notifications");
//        notificationPreference.setTitle("Enable message notifications");
//
//        PreferenceCategory notificationCategory = new PreferenceCategory(context);
//        notificationCategory.setKey("notifications_category");
//        notificationCategory.setTitle("Notifications");
//
//        Preference feedbackPreference = new Preference(context);
//        feedbackPreference.setKey("feedback");
//        feedbackPreference.setTitle("Send feedback");
//        feedbackPreference.setSummary("Report technical issues or suggest new features");
//
//        PreferenceCategory helpCategory = new PreferenceCategory(context);
//        helpCategory.setKey("help");
//        helpCategory.setTitle("Help");
//
//        screen.addPreference(notificationCategory);
//        screen.addPreference(helpCategory);
//
//        notificationCategory.addPreference(notificationPreference);
//        helpCategory.addPreference(feedbackPreference);
//
//        setPreferenceScreen(screen);
    }
}
