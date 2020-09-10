package com.csp.cases.activity;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Intent 相关工具类：补充
 * Created by csp on 20220/08/25
 * Modified by csp on 20220/08/25
 *
 * @version 1.0.0
 */
public class IntentSubUtils {

    /**
     * @param suffix     后缀，指定文件类型。* 或 ""：表示不设置文件类型筛选
     * @param specifyDir 打开指定路径目录，为 null 表示打开根目录，为文件则打开父目录。但实际要看机型
     * @see IIntentConstant#MIME_MAPPABLE
     */
    public static Intent getChooseFileIntent(File specifyDir, String suffix, boolean multiple) {
        if (specifyDir != null && specifyDir.isFile())
            specifyDir = specifyDir.getParentFile();

        Uri uri = specifyDir == null ? null : UriUtils.file2Uri((specifyDir));

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (uri != null)
            intent.setData(uri);

        intent.setType(IIntentConstant.MIME_MAP.get(suffix));
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiple); // 打开多个文件
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }
}
