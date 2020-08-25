package com.csp.cases.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenshp on 2018/6/15.
 */

public class FileActivity extends BaseListActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("文件选择（文本）", "choseFile_01", "文件选择"));
        items.add(new ItemInfo("文件选择（固定路径）", "choseFile_02", "文件选择"));
        items.add(new ItemInfo("文件选择（PDF）", "choseFile_03", "文件选择"));
        items.add(new ItemInfo("文件选择（PDF）", "choseFile_04", "文件选择"));
        return items;
    }

    private void choseFile_01() {
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setDataAndType(Uri.fromFile(dir.getParentFile()), "file/*.txt");
        //intent.setType("file/*.txt"); //华为手机mate7不支持
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 0);
    }

    private void choseFile_02() {
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(getExternalCacheDir()), "file/*.txt");
        //intent.setType("file/*.txt"); //华为手机mate7不支持
        //  intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 0);
    }

    private void choseFile_03() {
        //调用系统文件管理器打开指定路径目录
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //  intent.setDataAndType(Uri.fromFile(getExternalCacheDir()), "file/*.txt");
        //intent.setType("file/*.txt"); //华为手机mate7不支持
        intent.setType(map.get(".pdf"));
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 0);
    }

    private void choseFile_04() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);//打开多个文件
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件"), 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "请 安装文件管理器", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogCat.e("requestCode = %s, resultCode = %s\ndata = %s, ", requestCode, resultCode, data);

        if (data.getClipData() != null) {//有选择多个文件
            int count = data.getClipData().getItemCount();
            LogCat.e("url count ：  " + count);
            int currentItem = 0;

            while (currentItem < count) {
                Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                File file = UriUtils.uri2File(imageUri); // ContentUriUtil.getPath(this, imageUri);
                String imgpath = file == null ? null : file.getAbsolutePath();
                LogCat.e("url " + imgpath);

                //do something with the image (save it to some directory or whatever you need to do with it here)
                currentItem = currentItem + 1;
            }

        } else if (data.getData() != null) {//只有一个文件咯
            Uri imageUri =    data.getData();
            File file = UriUtils.uri2File(imageUri); // ContentUriUtil.getPath(this, data.getData());
            String imagePath = file == null ? null : file.getAbsolutePath();
            LogCat.e("Single image path ---- " + imagePath);

            //do something with the image (save it to some directory or whatever you need to do with it here)
        }

    }

    //{后缀名，MIME类型}
    private static final String[][] MIME_MapTable = {

            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    private static final Map<String, String> map = new HashMap<>();

    static {
        for (String[] value : MIME_MapTable) {
            map.put(value[0], value[1]);
        }
    }
}
