package com.csp.cases.activity.storage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.csp.cases.activity.SharedPreferencesActivity;
import com.csp.cases.activity.storage.database.SqliteActivity;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.constants.SystemConstant;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 文件存储案例
 * <p>Create Date: 2017/8/8 008
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class StorageActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("SharedPreferences", SharedPreferencesActivity.class, "偏好设置案例"));
        items.add(new ItemInfo("Databases", SqliteActivity.class, "数据库案例"));
        items.add(new ItemInfo("-----", "", ""));
        items.add(new ItemInfo("SD卡状态查询", "querySdcardState", "用于判定SD卡是否已经加载上"));
        items.add(new ItemInfo("存储目录获取", "queryStorageDir", "包括应用目录，SD卡相关目录"));
        items.add(new ItemInfo("存储情况查询", "queryStorageSize", ""));
        items.add(new ItemInfo("三级缓存", "", "TODO: 内存 -> 文件 -> 网络"));
        return items;
    }

    /**
     * SD卡状态查询
     */
    private void querySdcardState() {
        String state = Environment.getExternalStorageState();
        boolean removed = Environment.isExternalStorageRemovable();

        if (!Environment.MEDIA_MOUNTED.equals(state) || removed) {
            setDescription("SD卡不存在或未加载");
        } else {
            setDescription("SD卡已经加载完毕");
        }
    }

    /**
     * 存储目录获取
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void queryStorageDir() {
        String msg = "";

        File file = Environment.getExternalStorageDirectory();
        String path = file != null ? file.getAbsolutePath() : null;
        msg += "\nSD卡根目录: " + path;

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        path = file != null ? file.getAbsolutePath() : null;
        msg += "\nSD卡特定目录(如Music、DCIM等): " + path;

        file = getExternalFilesDir(null);
        path = file != null ? file.getAbsolutePath() : null;
        msg += "\nSD卡应用文件目录: " + path;

        file = getExternalCacheDir();
        path = file != null ? file.getAbsolutePath() : null;
        msg += "\nSD卡应用缓存目录: " + path;

        file = getFilesDir();
        path = file != null ? file.getAbsolutePath() : null;
        msg += "\n(Root)应用文件目录: " + path;

        file = getCacheDir();
        path = file != null ? file.getAbsolutePath() : null;
        msg += "\n(Root)应用缓存目录: " + path;

        file = getDatabasePath(SystemConstant.DATABASE_NAME);
        path = file != null ? file.getAbsolutePath() : null;
        msg += "\n(Root)应用数据库目录: " + path;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            file = getDataDir();
            path = file != null ? file.getAbsolutePath() : null;
            msg += "\n(Root)应用资源目录(Android 7.0以上): " + path;
        }
        LogCat.e(msg);
    }

    /**
     * 存储情况查询
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void queryStorageSize() {
        File sdcard = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(sdcard.getAbsolutePath());

        long blockTotal = sf.getBlockCountLong(); // 块总计
        long blockFree = sf.getFreeBlocksLong(); // 空闲块总计
        long blockAvailable = sf.getAvailableBlocksLong(); // 可用块总计
        long blockSize = sf.getBlockSizeLong(); // 块大小(B)

        long total = sf.getTotalBytes(); // 总空间大小(B) = blockTotal * blockSize
        long available = sf.getAvailableBytes(); // 总可用空间大小(B)
        long free = sf.getFreeBytes(); // 总空闲空间大小(B)

        float switchToGB = 1024 * 1024 * 1024; // B -> GB
        String msg = "total = blockTotal * blockSize";
        msg += "\nblockTotal : blockFree : blockAvailable : blockSize = ";
        msg += blockTotal + " : " + blockFree + " : " + blockAvailable + " : " + blockSize;
        msg += "\ntotal : available : free = ";
        msg += total + " : " + available + " : " + free;
        msg += "\ntotal(GB) : available(GB) : free(GB) = ";
        msg += total / switchToGB + " : " + available / switchToGB + " : " + free / switchToGB;
        LogCat.e(msg);
    }
}
