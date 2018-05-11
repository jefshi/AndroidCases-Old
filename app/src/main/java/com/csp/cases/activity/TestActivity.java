package com.csp.cases.activity;

import com.csp.cases.R;
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

        return items;
    }

    private void appInfoUtils() {
        int[] intArray = getResources().getIntArray(R.array.intArray);
        for (int datum : intArray) {
            LogCat.e(datum);

            imgItem.setImageResource(datum);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LogCat.printStackTrace(e);
            }
        }


//        LogCat.e(AppInfoUtils.getVersionName(this));
//
//        LogCat.e(AppInfoUtils.getVersionCode(this));
//
//        LogCat.e(AppInfoUtils.getSign(this, getPackageName()));
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