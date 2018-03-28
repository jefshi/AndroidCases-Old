package com.csp.cases.activity.view.arcmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.csp.cases.R;

/**
 * Created by chenshp on 2018/3/28.
 */

public class ArcActicity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sample);

        Integer[] resIds = new Integer[]{
                R.drawable.recorder_goon,
                R.drawable.recorder_pause,
                R.drawable.recorder_stop,
                R.drawable.recorder_start,
                R.drawable.recorder_browser
        };

        ArcAdapter adapter = new ArcAdapter(this, resIds);

        ArcLayout arcLayout = (ArcLayout) findViewById(R.id.arc);
        arcLayout.setAdapter(adapter);
    }
}
