package com.csp.cases.activity;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;
import com.csp.utils.android.phone.PhoneHardwareUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 度量以及屏幕参数案例
 * <p>Create Date: 2018/04/09
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class TestActivity extends BaseListActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("AppInfoUtils", "appInfoUtils", ""));
        items.add(new ItemInfo("PhoneHardwareUtils", "phoneHardwareUtils", ""));
        items.add(new ItemInfo("ToolBarActivity", ToolBarActivity.class, ""));

        return items;
    }

    private void appInfoUtils() {

//        try {
////            Class cls = Class.forName("com.android.launcher3.compat.AlphabeticIndexCompat2");
////            Method method = cls.getDeclaredMethod("computeSectionName");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

//        Method method = context.getClass().getDeclaredMethod(methodName);
//        method.setAccessible(true);
//        method.invoke(context);


//        boolean running = true;
//
//        // 第一种方法
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (running) {
//                    LogCat.e("请求一次网络");
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ignored) {
//                    }
//                }
//            }
//        }).start();
//
//        // 第二种方法
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LogCat.e("Handler：请求一次网络");
//
//                if (running)
//                    handler.postDelayed(this, 1000);
//            }
//        }, 1000);


//        LogCat.e(PhoneHardUtils.existBlueTooth());
//        LogCat.e(PhoneHardUtils.existLightSensor());
//        LogCat.e(Build.FINGERPRINT);
//        LogCat.e(Build.MODEL);
//        LogCat.e(Build.MANUFACTURER);
//        LogCat.e(Build.BRAND);
//        LogCat.e(Build.DEVICE);
//        LogCat.e(Build.PRODUCT);
//        LogCat.e(PhoneHardUtils.readCpuInfo());
//        LogCat.e(PhoneHardUtils.checkIsNotRealPhone());
    }

    private void phoneHardwareUtils() {
        LogCat.e(PhoneHardwareUtils.getSystemLanguage());
//        LogCat.e(PhoneHardwareUtils.getSystemLanguageList()); // TODO LogCat 有问题，有 702 个数据，但跳过了好多个
        LogCat.e(PhoneHardwareUtils.getSystemVersion());
        LogCat.e(PhoneHardwareUtils.getSystemModel());
        LogCat.e(PhoneHardwareUtils.getDeviceBrand());
        LogCat.e(PhoneHardwareUtils.getIMEI(this));
    }
}
