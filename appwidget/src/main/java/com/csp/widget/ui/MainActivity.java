package com.csp.widget.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.csp.widget.R;
import com.csp.widget.ui.gallery.GalleryAdapter;
import com.csp.widget.ui.gallery.ScaleTransformer;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;


public class MainActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.main_recycle1)
    RecyclerView mMainRecycle1;

    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LogCat.e(mMainRecycle1);

        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(mMainRecycle1, 0);
        layoutManager.setItemTransformer(new ScaleTransformer());

        mAdapter = new GalleryAdapter(this);
        mMainRecycle1.setAdapter(mAdapter);

        mMainRecycle1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GalleryLayoutManager) {
                    int curSelectedPosition = ((GalleryLayoutManager) layoutManager).getCurSelectedPosition();
                    LogCat.e(curSelectedPosition);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GalleryLayoutManager) {
                        int curSelectedPosition = ((GalleryLayoutManager) layoutManager).getCurSelectedPosition();
                        LogCat.e(curSelectedPosition);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        List<Integer> games = new ArrayList<>();
        games.add(R.drawable.img01);
        games.add(R.drawable.img02);
        games.add(R.drawable.img03);

        mAdapter.addData(games, false);
        mAdapter.notifyDataSetChanged();
    }
}
