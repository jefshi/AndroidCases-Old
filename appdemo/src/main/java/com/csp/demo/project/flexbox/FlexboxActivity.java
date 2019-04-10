package com.csp.demo.project.flexbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.csp.demo.R;

import java.util.ArrayList;
import java.util.List;

public class FlexboxActivity extends Activity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, FlexboxActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox);

//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
//        layoutManager.setFlexDirection(FlexDirection.COLUMN);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_END);

        FlexboxAdapter adapter = new FlexboxAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.rcv_content);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<String> data = new ArrayList<>();
        data.add("王立龙");
        data.add("花花");
        data.add("陈仕平");
        data.add("赵亚飞");
        data.add("李国伟");
        data.add("燕飞");
        data.add("王立龙");
        data.add("花花");
        data.add("陈仕平");
        data.add("赵亚飞");
        data.add("李国伟");
        data.add("燕飞");

        adapter.addData(data, false);
    }
}
