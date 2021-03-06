package com.csp.cases.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.csp.cases.CaseApp;

public class BaseFragment extends Fragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Context getContext() {
        Context context = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? super.getContext()
                : null;
        if (context != null)
            return context;

        context = getView() == null ? null : getView().getContext();
        if (context != null)
            return context;

        if (mContext != null)
            return mContext;

        return CaseApp.getApplication();
    }

}
