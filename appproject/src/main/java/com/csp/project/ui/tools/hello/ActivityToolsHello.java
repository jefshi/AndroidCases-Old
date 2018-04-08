package com.project.tools.hello;

import com.common.android.control.util.IntentUtil;
import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.hello.ActivityHello;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 欢迎界面步骤:
 *   1) 访问网络(一般是XML, 含版本号、图片的URL)
 *   2) 版本比较
 *   3) 加载图片
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-22 10:12:53
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ActivityToolsHello extends Activity implements OnClickListener {
	private TextView	txt;
	@SuppressWarnings("unused")
	private ImageView	img;

	private int		delayTime;	// 延迟时间(s)
	private boolean	isOpened;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools_hello);

		delayTime = 5;

		// 跳过功能实现
		txt = (TextView) findViewById(R.id.txtHelloSkip);
		txt.setText("跳过(" + delayTime + ")");
		txt.setOnClickListener(this);

		// 图片加载与入场动画
		img = (ImageView) findViewById(R.id.imgToolsHello);

		// 版本更新
		setVersionCode();

		// 跳转、倒计时
		final Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (delayTime >= 0) {
					// 更新UI
					delayTime--;
					txt.setText("跳过(" + delayTime + ")");

					if (delayTime == 0) {
						// 跳转页面
						skipPage();
					} else {
						// 继续延迟
						mHandler.postDelayed(this, 990);
					}
				}
			}
		}, 990);
	}

	/**
	 * 跳转页面
	 */
	public void skipPage() {
		if (isOpened) {
			return;
		}

		// 跳转
		IntentUtil.startActivity(ActivityToolsHello.this, ActivityHello.class);
		isOpened = true;

		// 关闭页面，防止回退
		finish();
	}

	/**
	 * 版本更新
	 */
	public void setVersionCode() {
		PackageManager pm = getPackageManager();

		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			LogUtil.logInfo("当前版本　: " + pi.versionCode);
			LogUtil.logInfo("当前版本号: " + pi.versionName);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.txtHelloSkip) {
			skipPage();
		}
	}

}
