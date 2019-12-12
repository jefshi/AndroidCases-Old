package com.csp.cases.activity.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class BitmapActivity extends BaseListActivity {

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("各种变换", "toMatrix", ""));
        return items;
    }

    private void toMatrix() {
        Bitmap bitmap = null;
        if (bitmap == null)
            return;

        // 缩放
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        // Android 2.3 以上，老 Bitmap 不需要 recycle，下同
        // if (recycle && !src.isRecycled() && ret != src) src.recycle();

        // 旋转
        matrix = new Matrix();
        matrix.setRotate(180);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

        // 左右翻转
        matrix = new Matrix();
        matrix.setScale(-1, 1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

}
