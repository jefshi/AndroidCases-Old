package com.csp.cases.activity.file;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshp on 2018/6/15.
 */

public class FileActivity extends BaseListActivity {

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("文件选择（任意类型 + 指定路径 + 多文件）", "choseFile_01", "文件选择"));
        items.add(new ItemInfo("文件选择（多种类型 + 指定路径 + 多文件）", "chooseMoreTypes", "文件选择"));
        items.add(new ItemInfo("文件选择（PDF）", "choseFile_02", "文件选择"));
        items.add(new ItemInfo("文件选择（TXT + 跟目录）", "choseFile_04", "文件选择"));
        return items;
    }

    private void choseFile_01() {
        Intent intent = IntentSubUtils.getChooseFileIntent(
                getExternalCacheDir(),
                "*",
                true);

        startActivityForResult(intent, 0);
    }

    final String DOC = "application/msword";
    final String XLS = "application/vnd.ms-excel";
    final String PPT = "application/vnd.ms-powerpoint";
    final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    final String XLSX = "application/x-excel";
    final String XLS1 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    final String PDF = "application/pdf";

    /**
     * 多种文件类型
     */
    private void chooseMoreTypes() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {DOC, DOCX, PDF, PPT, PPTX, XLS, XLS1, XLSX};
        intent.setType("application/*");

        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 1);
    }

    private void choseFile_04() {
        Intent intent = IntentSubUtils.getChooseFileIntent(
                getExternalCacheDir(),
                "txt",
                false);

        // TODO 使用createChooser还有一个好处就在于及时我们已经选择始终用某个方式打开，仍然可以弹出选择框进行选择。
        startActivityForResult(Intent.createChooser(intent, "请选择文件"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogCat.e("requestCode = %s, resultCode = %s\ndata = %s, ", requestCode, resultCode, data);

        // ===============================
        // 以上未解决
        // ===============================
        // content://com.android.providers.downloads.documents/document/msf:33137
        // content://com.android.providers.downloads.documents/document/535

        // content://com.android.shell.documents/document/bugreport:bugreport-EVR-AL00-HUAWEIEVR-AL00-2020-08-11-00-39-02-dumpstate_log-27034.txt
        // content://com.android.contacts/contacts/lookup/1885r157-51227EA77365/157

        // content://com.lcg.Xplore.FileContent/le//sdcard/.AResource/考虑考虑.txt
        // content://cn.wps.moffice_eng.fileprovider/external/Download/Browser/previewFile.pdf
        // content://cn.wps.moffice_eng.fileprovider/external_storage_root/Android/data/cn.wps.moffice_eng/.cache/KingsoftOffice/file/download/c7372e84-520d-439c-965b-e1f06ead0664/com.lcg.Xplore.FileContent/Hjj.pdf


        // ===============================
        // 以下已解决
        // ===============================


        // content://com.android.providers.media.documents/document/image:13775
        // content://com.android.providers.media.documents/document/video:13658
        // content://com.android.providers.media.documents/document/audio:33


        // content://media/external/audio/media/13779
        // content://media/external/images/media/13582
        // content://media/external/images/media/13630
        // content://media/external/audio/media/33
        // content://media/external/file/13268
        // content://media/external/file/31643

        // content://com.android.externalstorage.documents/document/primary:baidu/flyflow/kernel.log
        // content://com.android.externalstorage.documents/document/primary:APK/copy_src.patch
        // content://com.android.externalstorage.documents/document/primary:APK/TIM图片20200617164716.jpg
        // content://com.android.externalstorage.documents/document/primary:APK/copy_src.patch
        // content://com.android.externalstorage.documents/document/primary:耕雨健康/GYI.txt
        // content://com.android.externalstorage.documents/document/primary:APK/阿里巴巴Java.pdf


        // content://media/external/video/media
        // content://media/external/images/media
        // content://media/external/audio/media
        // content://contacts/people
        //
        // Intent.EXTRA_MIME_TYPES

        if (data.getClipData() != null) {//有选择多个文件
            int count = data.getClipData().getItemCount();
            LogCat.e("url count ：  " + count);
            int currentItem = 0;

            while (currentItem < count) {
                Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                // File file = UriUtils.uri2File(imageUri); // ContentUriUtil.getPath(this, imageUri);
//                String imgpath = file == null ? null : file.getAbsolutePath();
                String imagePath = ContentUriUtil.getPath(this, data.getData());
                LogCat.e("url " + imagePath);

                //do something with the image (save it to some directory or whatever you need to do with it here)
                currentItem = currentItem + 1;
            }

        } else if (data.getData() != null) {//只有一个文件咯
            Uri uri = data.getData();
            String imagePath = ContentUriUtil.getPath(this, data.getData());
            // String imagePath = file == null ? null : file.getAbsolutePath();
            LogCat.e("Single image path ---- " + imagePath);

            LogCat.e("uri.toString()", uri.toString());
            LogCat.e("uri.getAuthority()", uri.getAuthority());
            LogCat.e("uri.getScheme()", uri.getScheme());
            LogCat.e("uri.getPath()", uri.getPath());

            Context context = this;
            LogCat.e("isDocumentUri()", DocumentsContract.isDocumentUri(context, uri));
            if (DocumentsContract.isDocumentUri(context, uri))
                LogCat.e("getDocumentId()", DocumentsContract.getDocumentId(uri));


            //do something with the image (save it to some directory or whatever you need to do with it here)
        }
    }
}
