package com.csp.cases;

import android.Manifest;
import android.os.Bundle;
import android.util.Base64;

import com.csp.cases.activity.MetricsActivity;
import com.csp.cases.activity.OtherCaseActivity;
import com.csp.cases.activity.ProcessActivity;
import com.csp.cases.activity.SettingActivity;
import com.csp.cases.activity.TestActivity;
import com.csp.cases.activity.activity.ActivityActivity;
import com.csp.cases.activity.animation.AnimationActivity;
import com.csp.cases.activity.component.ComponentActivity;
import com.csp.cases.activity.file.FileActivity;
import com.csp.cases.activity.intent.IntentActivity;
import com.csp.cases.activity.network.NetworkActivity;
import com.csp.cases.activity.other.RecyclerActivity;
import com.csp.cases.activity.permissions.PermissionActivity;
import com.csp.cases.activity.storage.StorageActivity;
import com.csp.cases.activity.system.SystemActivity;
import com.csp.cases.activity.thread.ThreadActivity;
import com.csp.cases.activity.view.ViewActivity;
import com.csp.cases.activity.windowmanager.WindowManagerActivity;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.guides.GuidesActivity;
import com.csp.cases.step.StepActivity;
import com.csp.cases.zoom.ScaleViewActivity;
import com.csp.cases.zoom.ZoomImageActivity;
import com.csp.utils.android.log.LogCat;
import com.csp.utils.android.permissions.PermissionUtil;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseGridActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取权限
        String[] permissions = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        PermissionUtil.requestPermissions(this, permissions, 1000);
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("通用步骤 View", StepActivity.class, "自定义 View "));
        items.add(new ItemInfo("Guides", GuidesActivity.class, "官方 Android 开发指南"));
        items.add(new ItemInfo("Test", TestActivity.class, ""));
        items.add(new ItemInfo("Activity", ActivityActivity.class, "[Activity]案例"));
        items.add(new ItemInfo("Animation", AnimationActivity.class, "动画案例"));
        items.add(new ItemInfo("Component", ComponentActivity.class, "组件/控件案例"));
        items.add(new ItemInfo("Intent", IntentActivity.class, "意图案例"));
        items.add(new ItemInfo("Metrics", MetricsActivity.class, "度量以及屏幕参数案例"));
        items.add(new ItemInfo("Network", NetworkActivity.class, "网络案例"));
        items.add(new ItemInfo("Permission", PermissionActivity.class, "权限案例"));
        items.add(new ItemInfo("Process", ProcessActivity.class, "进程管理案例"));
        items.add(new ItemInfo("Setting", SettingActivity.class, "设置功能案例"));
        items.add(new ItemInfo("Storage", StorageActivity.class, "存储案例"));
        items.add(new ItemInfo("System", SystemActivity.class, "系统、应用信息案例"));
        items.add(new ItemInfo("Thread", ThreadActivity.class, "线程管理案例"));
        items.add(new ItemInfo("View", ViewActivity.class, "自定义 View、画布绘制、图片绘制案例"));
        items.add(new ItemInfo("WindowManager", WindowManagerActivity.class, "[WindowManager]案例"));
        items.add(new ItemInfo("OtherCase", OtherCaseActivity.class, "[其他]案例"));
        items.add(new ItemInfo("文件管理", FileActivity.class, "[文件]案例"));
        items.add(new ItemInfo("文件管理", FileActivity.class, "[文件]案例"));
        items.add(new ItemInfo("RSA 加密", "rsa", "[加密]案例"));
        items.add(new ItemInfo("ZoomImage", "zoomImage", "[加密]案例"));
        items.add(new ItemInfo("ScaleView", "scaleView", "[加密]案例"));
        items.add(new ItemInfo("列表", RecyclerActivity.class, "[列表]案例"));
        return items;
    }

    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWuAuSCrzUXC1l4ixXBeBfotUtkALrAjLM5UHiVfOFHrRJHM41HSeHVm56UZHgJlwk80R8juu1ykuhkgrilTv7H+3MpZdIunvndDElgdgk8aI2Ip4GUlemUDvCtWd3ychWEh4kYQ8CeInQvNM08imoLFldvbjWt/IkGK+BcGzamQIDAQAB";
    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJa4C5IKvNRcLWXiLFcF4F+i1S2QAusCMszlQeJV84UetEkczjUdJ4dWbnpRkeAmXCTzRHyO67XKS6GSCuKVO/sf7cyll0i6e+d0MSWB2CTxojYingZSV6ZQO8K1Z3fJyFYSHiRhDwJ4idC80zTyKagsWV29uNa38iQYr4FwbNqZAgMBAAECgYAxV1k6W1eMMg0OsKeRabQVuwoNG3tJEnQtDdSu0zKg3vdohAyh6MR7EvmiA7g86HH8CsPd/y/9WJe/8j6sBO0Ye9gt7eyQ2NiwWvlTuwNmngcSTapVvVI6NEyJFMfQt9PB1EHLNAXlz8jtJUyA7C48jReQD9p/SzAP0VxG7lwyMQJBAOjE7hAZ/6fyP3DB1fG7jr9gONZcz3TUaqx6BUn4GKZnckW08ht9Xqcqft5Hthu8BbLM9ptQ0U8QZekrJwD6ya0CQQClwstZMPu8jLhsgugVwodcG1mPEOiw9Yjnmt9+WTI07Ll2uFv//hRXBnahBBnZbucUYEbUY3kqUX9b3e9TmEodAkEAybPMbxt4VDoxCy6Mi/pxChkBZ4/pHV3sSiU6bAyWn6vIc+sGWRfca5MBePA/N+1IKtY9Y/02QwL8rH5+P/URyQJAL/hdjORGFdzLimuf6pwvPBKWKncEQCHuisghIZmClBpl2duklELddAnkztg2+tvDd/wcw14+NGb9aoKhvhl2aQJAbvcgoPU+xs0CjeexH+TS2S/jKkTRpvP2CpPK/k71m13xWdE8RtMkYY1measRmlIwOfWze7ll/PGT4dxWf31FNg==";
    private String dataRSA = "BlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBlankjBla12345678";


    private void rsa() {

        try {
            genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            LogCat.printStackTrace(e);
        }

        LogCat.e("公钥", publicKey);

        byte[] encrypt = EncryptUtils.encryptRSA(
                dataRSA.getBytes(),
                base64Decode(publicKey.getBytes()),
                true,
                "RSA/ECB/PKCS1Padding");

        LogCat.e("密文", new String(base64Encode(encrypt)));

        byte[] decrypt = EncryptUtils.decryptRSA(
                encrypt,
                base64Decode(privateKey.getBytes()),
                false,
                "RSA/ECB/PKCS1Padding");

        LogCat.e("原来的明文", dataRSA);
        LogCat.e("解密后明文", new String(decrypt));
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private static void genKeyPair() throws NoSuchAlgorithmException {

        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(1024, secureRandom);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Key publicKey = keyPair.getPublic();

        Key privateKey = keyPair.getPrivate();

        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        String publicKeyBase64 = EncodeUtils.base64Encode2String(publicKeyBytes);
        String privateKeyBase64 = EncodeUtils.base64Encode2String(privateKeyBytes);

        System.out.println("publicKeyBase64.length():" + publicKeyBase64.length());
        System.out.println("publicKeyBase64:" + publicKeyBase64);

        System.out.println("privateKeyBase64.length():" + privateKeyBase64.length());
        System.out.println("privateKeyBase64:" + privateKeyBase64);
    }

    private void zoomImage() {
        ZoomImageActivity.start(this);
    }

    private void scaleView() {
        ScaleViewActivity.start(this);
    }
}
