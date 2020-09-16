package com.csp.cases.activity.file;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import java.io.File;

/**
 * Uri --> File
 * Created by csp on 2020/09/16
 * Modified by csp on 2020/09/16
 *
 * @version 1.0.0
 */
public class UriToFileUtil {

    public static File toFile(final Context context, final Uri uri) {
        File file = toFileNoCheck(context, uri);
        return file != null && file.exists() ? file : null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static File toFileNoCheck(final Context context, final Uri uri) {
        if (uri == null)
            return null;

        // file:///
        if (isFileUri(uri)) {
            return new File(uri.getPath());
        }

        if (!isContentUri(uri))
            return null;

        // content://
        if (isQQDocument(uri))
            return getQQDocument(uri);

        if (isExternalStorageDocument(uri))
            return getExternalStorageDocument(uri);

        if (isDownloadsDocument(uri))
            return getDownloadsDocument(uri);

        if (isMediaDocument(uri))
            return getMediaDocument(context, uri);

        // 以下可行
        // content://media/external/audio/media/13823
        // content://media/external/images/media/13247
        // content://media/external/file/13268
        // content://com.meizu.media.gallery.store/external/images/media/71
        // content://cn.wps.moffice_eng.fileprovider/external/Download/Browser/previewFile.pdf
        // content://cn.wps.moffice_eng.fileprovider/external_storage_root/360Browser/Test.pdf
        // content://com.lcg.Xplore.FileContent/le//sdcard/360Browser/Test.pdf
        return getFileByCursor(context, uri, null, null);
    }

    /**
     * @return true：uri 是 content://
     */
    private static boolean isContentUri(Uri uri) {
        return uri != null && ContentResolver.SCHEME_CONTENT.equals(uri.getScheme());
    }

    /**
     * @return true：uri 是 file:///
     */
    private static boolean isFileUri(Uri uri) {
        return uri != null && ContentResolver.SCHEME_FILE.equals(uri.getScheme());
    }

    /**
     * *
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static File getFileByCursor(Context context, Uri uri, String selection, String[] selectionArgs) {
        if (context == null
                || uri == null)
            return null;

        final String column = MediaStore.Images.Media.DATA; // "_data"
        final String[] projection = {column};
        try (Cursor cursor = context.getContentResolver()
                .query(uri, projection, selection, selectionArgs, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                return new File(cursor.getString(
                        cursor.getColumnIndexOrThrow(column)));
            }
        }
        return null;
    }

    /**
     * @see #getMediaDocument(Context, Uri)
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 谷歌原生媒体文件选择
     * Uri：content://com.android.providers.media.documents/document/image:13044
     * Uri：content://com.android.providers.media.documents/document/video:13658
     * Uri：content://com.android.providers.media.documents/document/audio:35
     *
     * @see #isDownloadsDocument(Uri)
     */
    private static File getMediaDocument(Context context, Uri uri) {
        String path = uri.getPath();
        if (path == null)
            return null;

        Uri mediaUri = path.startsWith("/document/image:") ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                : path.startsWith("/document/video:") ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                : path.startsWith("/document/audio:") ? MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                : null;

        if (mediaUri == null)
            return null;

        String id = path.substring(path.indexOf(":") + 1);
        String selection = MediaStore.Images.Media._ID + "=?"; // _id=?
        String[] selectionArgs = new String[]{id};
        return getFileByCursor(context, mediaUri, selection, selectionArgs);
    }

    /**
     * @see #getDownloadsDocument(Uri)
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 谷歌原生文件下载
     * Uri：content://com.android.providers.downloads.documents/document/msf:13776
     * File：无解
     * <p>
     * Uri：content://com.android.providers.downloads.documents/document/9
     * File：无解
     * <p>
     * Uri：content://com.android.providers.downloads.documents/document/raw:/storage/emulated/0/Download/APK/copy_src.patch
     * File：/storage/emulated/0/Download/APK/copy_src.patch
     *
     * @see #isDownloadsDocument(Uri)
     */
    private static File getDownloadsDocument(Uri uri) {
        String path = uri.getPath();
        String key = "/document/raw:";
        return path == null || !path.startsWith(key)
                ? null
                : new File(path.substring(key.length()));
    }

    /**
     * @see #getExternalStorageDocument(Uri)
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * 谷歌原生文件管理器（支持过滤）
     * Uri：content://com.android.externalstorage.documents/document/primary:APK/copy_src.patch
     * File：/storage/emulated/0/APK/copy_src.patch
     *
     * @see #isExternalStorageDocument(Uri)
     */
    private static File getExternalStorageDocument(Uri uri) {
        String path = uri.getPath();
        String key = "/document/primary:";
        if (path == null || !path.startsWith(key))
            return null;

        File sdCard = Environment.getExternalStorageDirectory();
        return new File(sdCard, path.substring(key.length()));
    }

    /**
     * @see #getQQDocument(Uri)
     */
    private static boolean isQQDocument(Uri uri) {
        return "com.tencent.mtt.fileprovider".equals(uri.getAuthority());
    }

    /**
     * 特殊：QQ 文件管理器
     * Uri：content://com.tencent.mtt.fileprovider/QQBrowser/APK/demo.mp4
     * File：/storage/emulated/0/APK/demo.mp4
     *
     * @see #isQQDocument(Uri)
     */
    private static File getQQDocument(Uri uri) {
        String path = uri.getPath();
        String key = "/QQBrowser";
        if (path == null || !path.startsWith(key))
            return null;

        File sdCard = Environment.getExternalStorageDirectory();
        return new File(sdCard, path.substring(key.length()));
    }
}
