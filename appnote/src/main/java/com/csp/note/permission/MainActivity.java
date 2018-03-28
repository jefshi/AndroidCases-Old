package com.csp.permissiondemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private String[] permissions = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // requestPermissions();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBackup) {
            if (checkPermissions(permissions)) {
                doBackup();
            } else {
                /**
                 * 第 2 步: 请求权限
                 */
                // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
            }
        }
    }

    /**
     * 第 4 步: 备份通讯录操作
     */
    private void doBackup() {
        // 本文主旨是讲解如果动态申请权限, 具体备份代码不再展示, 就假装备份一下
        Toast.makeText(this, "正在备份通讯录...", Toast.LENGTH_SHORT).show();
    }

    private void requestPermissions() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissions(permissions);

        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("--permission result: ", requestCode + "");

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            // 如果所有的权限都授予了, 则执行备份代码
            if (isAllGranted) {
                return;
            }

            // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
            openAppDetails();
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
