package com.csp.cases.activity.file;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.File;
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

        if (resultCode != -1)
            return;


        // ===============================
        // 以上未解决
        // ===============================
        // content://com.android.providers.downloads.documents/document/msf:33137
        // content://com.android.providers.downloads.documents/document/535

        // content://com.android.shell.documents/document/bugreport:bugreport-EVR-AL00-HUAWEIEVR-AL00-2020-08-11-00-39-02-dumpstate_log-27034.txt
        // content://com.android.contacts/contacts/lookup/1885r157-51227EA77365/157

        ClipData clipData = data.getClipData();
        Uri uri = data.getData();
        if (clipData == null && uri != null) {
            File file = UriToFileUtil.toFile(this, uri);

            LogCat.e("file", file);
            logUriInfo(uri, 0);
        }
        if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                uri = clipData.getItemAt(i).getUri();
                File file = UriToFileUtil.toFile(this, uri);

                LogCat.e("file", file);
                logUriInfo(uri, i);
            }
        }
    }

    private void logUriInfo(Uri uri, int index) {
        LogCat.e("[%s]uri.toString(): %s", index, uri.toString());
        LogCat.e("[%s]uri.getAuthority(): %s", index, uri.getAuthority());
        LogCat.e("[%s]uri.getScheme(): %s", index, uri.getScheme());
        LogCat.e("[%s]uri.getPath(): %s", index, uri.getPath());

        Context context = this;
        LogCat.e("[%s]isDocumentUri(): %s", index, DocumentsContract.isDocumentUri(context, uri));
        if (DocumentsContract.isDocumentUri(context, uri))
            LogCat.e("[%s]getDocumentId(): %s", index, DocumentsContract.getDocumentId(uri));
    }
}
