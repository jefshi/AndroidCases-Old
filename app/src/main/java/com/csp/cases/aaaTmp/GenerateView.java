package com.csp.cases.aaaTmp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.csp.cases.R;

/**
 * Description:
 * Create Date: 2017/7/4
 * Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class GenerateView extends Activity {

    /**
     * 解析XML布局文件
     * 非常耗时与麻烦
     */
    public View getView(Context context, int resId, ViewGroup parent) {
        // 方法01
        View view = getInflater(context).inflate(resId, parent, false);

        // 方法02
        // View view = View.inflate(context, resId, parent);

        return view;
    }

    /**
     * 获取用于解析XML布局文件的填充器
     */
    public LayoutInflater getInflater(Context context) {
        // 方法01
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 方法02
        // LayoutInflater inflater = LayoutInflater.from(context);

        return inflater;
    }

    /**
     * 获取资源ID, 根据资源名称
     *
     * @param name    资源文件名称
     * @param defType 资源类型: "animator"
     * @return 资源ID
     */
    protected int getResourceId(String name, String defType) {
        return getResources().getIdentifier(name, defType, getPackageName());
    }

    /**
     * 设置控件图片
     */
    public void setImage(ImageView img) {
        // 方法01:
        img.setImageResource(R.mipmap.ic_launcher);

        // 方法02:
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        img.setImageDrawable(drawable);
    }

}
