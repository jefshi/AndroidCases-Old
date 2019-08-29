package com.csp.cases.guides.user_interface.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.SwitchPreferenceCompat;

/**
 * Build a hierarchy in code
 * https://developer.android.google.cn/guide/topics/ui/settings/programmatic-hierarchy
 */
public class SettingByJavaFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        SwitchPreferenceCompat notificationPreference = new SwitchPreferenceCompat(context);
        notificationPreference.setKey("notifications");
        notificationPreference.setTitle("Enable message notifications");

        PreferenceCategory notificationCategory = new PreferenceCategory(context);
        notificationCategory.setKey("notifications_category");
        notificationCategory.setTitle("Notifications");

        Preference feedbackPreference = new Preference(context);
        feedbackPreference.setKey("feedback");
        feedbackPreference.setTitle("Send feedback");
        feedbackPreference.setSummary("Report technical issues or suggest new features");

        PreferenceCategory helpCategory = new PreferenceCategory(context);
        helpCategory.setKey("help");
        helpCategory.setTitle("Help");

        screen.addPreference(notificationCategory);
        screen.addPreference(helpCategory);

        notificationCategory.addPreference(notificationPreference);
        helpCategory.addPreference(feedbackPreference);

        setPreferenceScreen(screen);
    }
}
