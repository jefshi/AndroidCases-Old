package com.csp.cases.activity.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;

import com.csp.cases.BuildConfig;
import com.csp.cases.activity.component.broadcast.StaticReceiver;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.IntentUtils;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Intent 应用案例
 * <p>Create Date: 2017/9/5
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class IntentActivity extends BaseGridActivity {

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(new ItemInfo("数据传输", "transfer", "通过实现[Serializable, Parcelable]完成数据传输"));
        itemInfos.add(new ItemInfo("打开相机", "openCamera", ""));
        itemInfos.add(new ItemInfo("打开图册", "openAlbum", ""));
        return itemInfos;
    }

    /**
     * 数据传输
     */
    private void transfer() {
        Intent intent = new Intent(StaticReceiver.RECEIVER_ACTION);
        intent.putExtra("Serializable", new OptionBySer(10, 20));
        intent.putExtra("Parcelable", new OptionByPar(15, 25));
        sendBroadcast(intent, StaticReceiver.PERMISSION);
    }

    private void openCamera() {
        File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        LogCat.e(picture);
        File file = new File(picture, "temp.png");


//        com.nijigenlink.gb.fileProvider;
//
//        getPackageName() + "fileProvider";

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
        } else
            uri = Uri.fromFile(file);

        LogCat.e("uri", uri);

        Intent intent = IntentUtils.getCaptureIntent(uri);
        startActivityForResult(intent, 1011);
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, new Parcelable[0]);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivityForResult(intent, 1012);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogCat.e(requestCode + ": ", resultCode);

        File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(picture, "temp.png");
        File outfile = new File(picture, "out.png");

        Uri inUri;
        Uri outUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            inUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            outUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", outfile);
        } else {
            inUri = Uri.fromFile(file);
            outUri = Uri.fromFile(outfile);
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == 1011) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Uri contentUri = FileProvider.getUriForFile(getActivity(), Constant.PortraitPhoto.FILEPROVIDER_AUTHORITY, captureFile);
//                    cropPhoto(contentUri);
//                } else {
//                    cropPhoto(Uri.fromFile(captureFile));
//                }

                IntentUtils.buildImageCropIntent(inUri, outUri, 300, 300, false);
            }

            if (requestCode == 1012) {

            }
        }

    }
}
