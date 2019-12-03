package com.csp.cases.activity.network.downfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.classutil.BitmapUtil;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by csp on 2018/5/15 015.
 */

public class DownFileActivity extends BaseListActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> itemInfos = new ArrayList<>();

        itemInfos.add(new ItemInfo("下载并保存图片", "downFile", ""));


        return itemInfos;
    }

    private void downFile() {
        loadImage("http://img07.tooopen.com/images/20170818/tooopen_sy_220999936848.jpg");
    }

    private void loadImage(final String url) {
        // 图片保存路径
        String fileName = url.substring(url.lastIndexOf('/'));
        File scCard = Environment.getExternalStorageDirectory();
        final File saveFile = new File(scCard, "/APK/" + fileName);
        LogCat.e("图片保存位置：", saveFile.getAbsoluteFile());

        // 加载图片
        Request request = new Request.Builder().url(url).build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogCat.printStackTrace(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                try {
                    ResponseBody body = response.body();
                    if (body == null)
                        return;

                    is = body.byteStream();
                    Bitmap bitmap = BitmapUtil.toBitmap(is);
                    onSaveBitmap(bitmap, saveFile);
                } catch (Exception e) {
                    LogCat.printStackTrace(e);
                } finally {
                    if (is != null)
                        is.close();
                }
            }
        });
    }

    private void onSaveBitmap(Bitmap mBitmap, File saveFile) {
        if (saveFile.exists())
            saveFile.delete();

        File parent = saveFile.getParentFile();
        if (!parent.exists())
            parent.mkdirs();

        try {
            saveFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveFile);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            LogCat.printStackTrace(e);
        }
    }

    private void onSaveBitma01(Bitmap mBitmap, Context context) {
        new Thread(() -> {
            String photoPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/APK/" + "test.jpg";
            //创建文件对象，用来存储新的图像文件
            File file = new File(photoPath);
            //创建文件
            try {
                file.createNewFile();
                //定义文件输出流
                FileOutputStream fOut = new FileOutputStream(file);
                //将bitmap存储为jpg格式的图片
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();//刷新文件流
                fOut.close();

                //文件存储已经完毕，保存的图片没有加入到系统图库中
                //，此时需要发送广播，刷新图库，很简单几行代码搞定
                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(new File(photoPath));
                intent.setData(uri);
                context.sendBroadcast(intent);
            } catch (IOException e) {
                LogCat.printStackTrace(e);
            }
        }).start();
    }

}
