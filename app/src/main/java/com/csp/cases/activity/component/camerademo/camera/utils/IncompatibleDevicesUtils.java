package com.csp.cases.activity.component.camerademo.camera.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 不兼容设备
 * <p>
 * 参考 Github：https://github.com/infinum/Android-GoldenEye
 * <p>
 * Created by infinum on 2018/08/08
 * Modified by csp on 2019/12/06
 *
 * @version 1.0.0
 */
public class IncompatibleDevicesUtils {

    public static boolean isIncompatibleDevice(String model) {
        Device[] devices = Device.values();
        for (Device device : devices) {
            for (String name : device.mNames) {
                if (name.equals(model))
                    return true;
            }
        }
        return false;
    }

    private enum Device {

        ONE_PLUS_6(Arrays.asList("oneplus a6000", "oneplus a6003")),
        REDMI(Arrays.asList("Redmi K20 Pro")); // 小米系列估计都得进黑名单

        private List<String> mNames;

        Device(List<String> names) {
            mNames = names;
        }
    }
}