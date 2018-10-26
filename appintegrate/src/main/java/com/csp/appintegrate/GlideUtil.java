package com.csp.appintegrate;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nijigenlink.gb.R;
import com.nijigenlink.utillib.EmptyUtil;
import com.nijigenlink.utillib.LogCat;

import java.io.File;
import java.util.List;

public class GlideUtil {

    public static void loadDefault(Context context, ImageView img, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.shape_bg_icon)
                .error(R.drawable.shape_bg_icon)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(img);
    }

    public static void getCacheFile(final Context context, final String[] urls) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (EmptyUtil.isEmpty(urls))
                    return;

                for (String url : urls) {
                    if (EmptyUtil.isEmpty(url))
                        return;

                    File file = null;
                    try {
                        file = Glide.with(context)
                                .downloadOnly()
                                .load(url)
                                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                    } catch (Exception ex) {
                        LogCat.printStackTrace(Log.WARN, "Sharing " + url + " failed", ex);
                    }

                    if (file != null) {
                        String filePath = file.getAbsolutePath();
                        TblPictureOperate.getInstance().updateFilePath(url, filePath);
                    }
                }
            }
        }).start();
    }

    public static void getCacheFile(final Context context, String url) {
        getCacheFile(context, new String[]{url});
    }

    public static void getCacheFile(final Context context, List<String> urls) {
        String[] urlArray = new String[urls.size()];
        urls.toArray(urlArray);
        getCacheFile(context, urlArray);
    }
}
