package com.csp.cases.activity.component;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.csp.cases.R;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.dialog.PopupWindowHelper;
import com.csp.utils.android.dialog.PopupWindowLayoutGravity;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Description: 对话框案例
 * <p>Create Date: 2017/07/25
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class DialogActivity extends BaseGridActivity {
    private String[] items = new String[]{"北京", "上海", "深圳", "广州"};

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("警告对话框：普通", "alerDialog01", ""));
        items.add(new ItemInfo("警告对话框：列表", "alerDialog02", ""));
        items.add(new ItemInfo("警告对话框：单选", "alerDialog03", ""));
        items.add(new ItemInfo("警告对话框：复选", "alerDialog04", ""));
        items.add(new ItemInfo("警告对话框：自定义View", "alerDialog05", "注：含输入控件的对话框，无法自动弹出输入法时，见详细代码"));
        items.add(new ItemInfo("警告对话框：Activity", "", "不提共案例。方法：将[Activity]的主题改为[Dialog]形式即可"));
        items.add(new ItemInfo("日期对话框", "datePickerDialog01", ""));
        items.add(new ItemInfo("时间对话框", "timePickerDialog01", ""));
        items.add(new ItemInfo("进度对话框", "progressDialog01", ""));
        items.add(new ItemInfo("-----", "", ""));

        items.add(new ItemInfo("PopupWindow 指定锚点方位", "showPopupWindow", ""));

        return items;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        LogCat.e(toString() + "生命周期：onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();

        LogCat.e(toString() + "生命周期：onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        LogCat.e(toString() + "生命周期：onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        LogCat.e(toString() + "生命周期：onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        LogCat.e(toString() + "生命周期：onStop");
    }

    /**
     * 警告对话框：普通
     */
    private void alerDialog01() {
        final Activity activity = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.item_card)
                .setTitle("退出")
                .setMessage("您确定返回吗？")
                .setCancelable(false); // 不能以按下"back"键的方式退出对话框

        // 确定
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.onBackPressed();
            }
        });

        // 取消
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // 调用[OnCancelListener]事件
                // dialog.dismiss();
                // dialog.hide(); // 只隐藏
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                LogCat.e("Dialog.OnCancelListener");
            }
        });

        // 显示Dialog
        // builder.create().show();
        builder.show();
    }

    /**
     * 警告对话框：列表
     */
    private void alerDialog02() {
        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogCat.e("选中: ", items[which]);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 警告对话框：单选
     */
    private void alerDialog03() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogCat.e("选中: ", items[which]);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 警告对话框：复选
     */
    private void alerDialog04() {
        DialogInterface.OnMultiChoiceClickListener listener =
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        LogCat.e("选中: ", items[which] + ", " + isChecked);
                    }
                };

        new AlertDialog.Builder(this)
                .setMultiChoiceItems(items, new boolean[items.length], listener)
                .setPositiveButton("确定", null)
                .show();
    }


    /**
     * 警告对话框：自定义View
     */
    private void alerDialog05() {
        // 自定义[View]
        // View view = View.inflate(this, R.animator.activity_1012_event, null);
        EditText edt = new EditText(this);
        edt.setText("自定义View: EditText");

        // 添加[View]
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.item_card)
                .setTitle("查看通知内容")
                .setView(edt)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        // 弹出输入法
        if (dialog.getWindow() != null)
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 日期对话框
     */
    private void datePickerDialog01() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                int flags = DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_WEEKDAY;

                String title = DateUtils.formatDateTime(
                        DialogActivity.this,
                        mCalendar.getTimeInMillis(),
                        flags);
                setDescription(title);
            }
        };

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        dialog.show();
    }

    /**
     * 时间对话框
     */
    private void timePickerDialog01() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setDescription(hourOfDay + " : " + minute);
            }
        };

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                listener,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    /**
     * 进度对话框
     */
    private void progressDialog01() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("加载中...");
        dialog.setIcon(R.mipmap.item_card);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setIndeterminate(false); // 对转轮模式没有实际意义
        dialog.show();

        // 设置进度的有效条件：条状样式，setIndeterminate(false)，show()之后
        dialog.setProgress(50);
        dialog.incrementProgressBy(10);
    }

    private void showPopupWindow() {
//        Context context = this;
//        final PopupWindowLayoutGravity layoutGravity = new PopupWindowLayoutGravity(
//                PopupWindowLayoutGravity.ALIGN_RIGHT | PopupWindowLayoutGravity.TO_BOTTOM);
//
//        final PopupWindow hPopupWindow = new PopupWindowHelper.Builder()
//                .setView(new RecyclerView(context))
//                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
//                .setHeight(WindowManager.LayoutParams.WRAP_CONTENT)
//                .create(context)
//                .showBashOfAnchor(mTxtOilMeasure, layoutGravity, 0, 0);
//
//        // PopupWindow 内容
//        List<EverydayRecord.DataBean.OilTypeListBean> oilTypeList = getPresenter().getOilTypes();
//        ListSimpleAdapter<EverydayRecord.DataBean.OilTypeListBean> adapter = new ListSimpleAdapter<>(context);
//        adapter.addData(oilTypeList, false);
//
//        RecyclerView rcv = (RecyclerView) hPopupWindow.getContentView();
//        rcv.setLayoutManager(new LinearLayoutManager(context));
//        rcv.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new MultipleAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(ViewGroup parent, View view, ItemViewHolder holder, int position) {
//                getPresenter().setOilTypeIndex(position);
//                refreshOilType();
//                hPopupWindow.dismiss();
//            }
//        });
    }
}