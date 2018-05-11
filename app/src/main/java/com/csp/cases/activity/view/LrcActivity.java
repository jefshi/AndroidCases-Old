package com.csp.cases.activity.view;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.csp.cases.R;
import com.csp.view.LrcView02;
import com.csp.view.loadingtextview.LoadingTextsView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LrcActivity extends Activity {
    private LrcView02 mLrc;
    private MediaPlayer mPlayer;
    private String mDir = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_lrc);

        List<String> contents = new ArrayList<>();
        contents.add("正在连接次元连接主服务器…完毕");
        contents.add("正在智能选择最佳路线…完毕");
        contents.add("正在智能选择最佳路线…完毕");
        contents.add("正在智能选择最佳路线…完毕");
        contents.add("正在智能选择最佳路线…完毕");

        final Handler handler = new Handler();


        final LoadingTextsView loading = findViewById(R.id.loading);
        loading.setOnScrollListener(() ->
                handler.postDelayed(() -> {
                    loading.addContent("正在智能选择最佳路线…完毕");
                    loading.startScroll();
                }, 100)
        );
//        handler.postDelayed(loading::startScroll, 1000);
        loading.addContent("正在连接次元连接主服务器…完毕");
        loading.startScroll();



//        loading.startScroll();

//        loading.setContents(contents);
//        handler.postDelayed(() -> {
//            Toast.makeText(this, "1000", Toast.LENGTH_SHORT).show();
//            LogCat.e(1000);
//            loading.startScroll();
////            loading.startScroll();
//        }, 1000);

//        handler.postDelayed(() -> {
//            String tip = "ヽ(。>д<)ｐ加速失败 请检查网络重新";
//            loading.replaceContent(contents.size() - 1, tip);
//            loading.addContent(tip);
//            loading.startScroll();
//        }, 4000);
//
//        handler.postDelayed(() -> {
//            Toast.makeText(this, "5000", Toast.LENGTH_SHORT).show();
//            loading.startScroll();
//        }, 5000);


        // 歌曲路径
        String music = mDir + "1.mp3";
        // 歌词路径
        String lrc = mDir + "1.lrc";

        mLrc = (LrcView02) findViewById(R.id.lrc);
        // 设置背景图片
        // 可以选择不设置
        // 最好是在真个屏幕设置该图片
        // 那样更好看
        mLrc.setBackground(BitmapFactory.decodeFile(mDir + "1.png"));

        mPlayer = new MediaPlayer();
        try {
//            mPlayer.setDataSource(music);
//            mPlayer.setOnPreparedListener(new PreparedListener());
//            mPlayer.prepareAsync();

            // 设置lrc的路径
            mLrc.setLrcPath(lrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {

//            mPlayer.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 当歌曲还在播放时
                    // 就一直调用changeCurrent方法
                    // 虽然一直调用， 但界面不会一直刷新
                    // 只有当唱到下一句时才刷新
                    while (mPlayer.isPlaying()) {
                        // 调用changeCurrent方法， 参数是当前播放的位置
                        // LrcView会自动判断需不需要下一行
                        mLrc.changeCurrent(mPlayer.getCurrentPosition());

                        // 当然这里还是要睡一会的啦
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}