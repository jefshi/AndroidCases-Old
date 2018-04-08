package com.csp.project.common;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.csp.project.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成[item]可执行的指定方法的[GridView]
 *
 * @author csp
 *         <p style='font-weight:bold'>Date:</p> 2016年10月17日 下午8:34:24
 *         <p style='font-weight:bold'>AlterDate:</p>
 * @version 1.0
 */
public class FunctionGridView implements OnItemClickListener {
    private Context context;    // GridView 所在页面
    private int position;    // GridView 被点击的[Item]的位置

    private List<String> itemNames;        // [GridView]数据源
    private List<String> methodNames;    // 方法名称
    private List<Boolean> isEmphasized;    // [GridView]的[item]的文本强调记录

    private final static int EMPHASIZE_COLOR = Color.RED;    // 强调颜色
    private final static int ERROR_COLOR = Color.RED;    // 错误颜色
    private final static int NORMAL_COLOR = 0xFFCBCBCB;    // 正常的颜色
    private final static int CLICK_COLOR = Color.YELLOW;    // 点击后闪现的颜色

    {
        itemNames = new ArrayList<String>();
        methodNames = new ArrayList<String>();
        isEmphasized = new ArrayList<Boolean>();
        position = 0;
    }

    /**
     * 构造方法
     */
    public FunctionGridView(Context context) {
        this.context = context;
    }

    /**
     * 获取当前被点击的[item]的位置
     */
    public int getPosition() {
        return position;
    }

    /**
     * 添加数据源
     */
    public void addData(String itemName, String methodName, boolean isRed) {
        itemNames.add(itemName);
        methodNames.add(methodName);
        isEmphasized.add(isRed);
    }

    /**
     * 获取[item]个数
     */
    public int getCount() {
        return itemNames.size();
    }

    /**
     * 设置[GridView]
     *
     * @param view 指定的GridView
     */
    public void setGridView(View view) {
        GridView grv = (GridView) view;

        // [GridView]适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.item_project, itemNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setLines(1);

                if (isEmphasized.get(position)) {
                    view.setTextColor(EMPHASIZE_COLOR);
                }
                return view;
            }
        };
        grv.setAdapter(adapter);

        // [GridView]事件
        grv.setOnItemClickListener(this);
    }

    @Override
    /**
     * [item]点击事件: 通过方法指针(反射), 执行指定方法
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         * 记录当前被点击的位置
         */
        this.position = position;

        /**
         * 执行方法
         */
        boolean isError = false;
        try {
            // 获取方法名
            String methodName = methodNames.get(position);
            if (methodName == null || "".equals(methodName)) {
                return;
            }

            // 执行方法
            Method method = context.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(context);
        } catch (Exception e1) {
            isError = true;
            e1.printStackTrace();
        }

        /**
         * 改变被点击项的背景色
         */
        final View itemView = view;
        final int color = isError ? ERROR_COLOR : NORMAL_COLOR;
        itemView.setBackgroundColor(CLICK_COLOR);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                itemView.setBackgroundColor(color);
            }
        }, 10);
    }
}
