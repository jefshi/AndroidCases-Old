package com.csp.utils.android.permissions;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.HashMap;
import java.util.Set;

/**
 * Description: 动态权限获取弹窗
 * <p>Create Date: 2018/03/27
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PermissionDialog extends AlertDialog.Builder {
    private Context context;
    private HashMap<String, String> permissions = new HashMap<>();
    private int requestCode;

    public PermissionDialog(Context context) {
        super(context);
        this.context = context;
    }

    public PermissionDialog setPermissions(HashMap<String, String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public PermissionDialog setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @Override
    public AlertDialog show() {
        StringBuilder tip = new StringBuilder();
        Set<String> keys = permissions.keySet();
        for (String key : keys) {
            tip.append(permissions.get(key)).append(", ");
        }
        tip.delete(tip.length() - 2, tip.length());

        this.setMessage("本应用需要获取以下权限: " + tip.toString())
                .setPositiveButton("手动授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context instanceof Activity)
                            PermissionUtil.openPermissionsSetting((Activity) context, permissions.keySet(), requestCode);

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context instanceof Activity)
                            ((Activity) context).finish();

                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (context instanceof Activity)
                            PermissionUtil.requestMustPermissions((Activity) context, permissions, requestCode);

                        dialog.dismiss();
                    }
                });
        return super.show();
    }
}
