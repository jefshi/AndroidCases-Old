package com.csp.cases.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.csp.cases.R;
import com.csp.utils.android.log.LogCat;

public class ToolBarActivity extends Activity {
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tool_barr);

        toolbar = findViewById(R.id.toolbar);

        // toolbar.setLogo(R.mipmap.item_card);
        //title
        toolbar.setTitle("Material Design ToolBar");
        toolbar.setTitleTextAppearance();
        //sub title
        // toolbar.setSubtitle("  ToolBar subtitle");

        //设置导航Icon，必须在setSupportActionBar(toolbar)之后设置
        // toolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        //添加菜单点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogCat.e("onClick");
                // Snackbar.make(toolbar, "Click setNavigationIcon", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
