package com.csp.cases.guides.user_interface.settings;

import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceDataStore;

import java.util.HashMap;
import java.util.Map;

public class DataStore extends PreferenceDataStore {

    private Map<String, String> mapStr = new HashMap<>();
    private Map<String, Boolean> mapBoolean = new HashMap<>();

    @Override
    public void putString(String key, @Nullable String value) {
        mapStr.put(key, value == null ? "" : value);
    }

    @Override
    @Nullable
    public String getString(String key, @Nullable String defValue) {
        String value = mapStr.get(key);
        return value == null ? defValue : value;
    }

    @Override
    public void putBoolean(String key, boolean value) {
        mapBoolean.put(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        Boolean value = mapBoolean.get(key);
        return value == null ? defValue : value;
    }
}
