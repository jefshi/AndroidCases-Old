package com.csp.cases.base.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.csp.cases.R;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.base.adapter.BaseListAdapter;
import com.csp.utils.android.log.LogCat;
import com.csp.library.java.EmptyUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description: 项目列表适配器(ListView)
 * <p>Create Date: 2017/7/4
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class ItemAdapter extends BaseListAdapter<ItemInfo> implements AdapterView.OnItemClickListener {
	public final static String KEY_DESCRIPTION = "KEY_DESCRIPTION";
	private Context context;

	public ItemAdapter(Context context) {
		super(context, null, R.layout.item);
	}

	public ItemAdapter(Context context, List<ItemInfo> objects) {
		super(context, objects, R.layout.item);
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Context context = view.getContext();
		ViewHolder vHolder = getViewHolder(view);
		final ItemInfo object = getItem(vHolder.getPosition());

		// 跳转页面
		Class<? extends Activity> jumpClass = object.getJumpClass();
		if (jumpClass != null) {
			Intent intent = new Intent(context, object.getJumpClass());
			intent.putExtra(KEY_DESCRIPTION, object.getDescription());
			context.startActivity(intent);
			return;
		}

		// 执行方法
		if (object.isWorkThread()) {
			excuteByAsync(new Runnable() {
				@Override
				public void run() {
					excuteMethod(object);
				}
			});
		} else {
			excuteMethod(object);
		}
	}

	/**
	 * 执行方法
	 */
	private void excuteMethod(ItemInfo object) {
		String methodName = object.getMethodName();
		if (EmptyUtil.isBank(methodName)) {
			return;
		}

		boolean error = false;
		try {
			Method method = context.getClass().getDeclaredMethod(methodName);
			method.setAccessible(true);
			method.invoke(context);
		} catch (Exception e) {
			LogCat.printStackTrace(e);
			error = true;
		}
		if (error != object.isError()) {
			object.setError(error);
			notifyDataSetChanged();
		}
	}

	/**
	 * 异步执行方法
	 */
	private void excuteByAsync(Runnable task) {
		final HandlerThread work = new HandlerThread(this.getClass().getSimpleName());
		work.start();

		Message message = Message.obtain();
		message.what = 0;
		message.obj = task;

		Handler handler = new TaskHandler(work.getLooper());
		handler.sendMessage(message);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void refreshViewContent(ViewHolder vHolder, ItemInfo object) {
		vHolder.txtItem.setText(object.getName());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			vHolder.txtItem.setActivated(object.isError());
		}
	}

	private void setViewEvent(final ViewHolder vHolder) {
	}

	// ========================================
	// 异步任务
	// ========================================
	private static class TaskHandler extends Handler {

		public TaskHandler(Looper looper) {
			super(looper);
		}

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				((Runnable) msg.obj).run();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
					getLooper().quitSafely();
				} else {
					getLooper().quit();
				}
			}
		}
	}

	// ========================================
	// Item 内容设置: ViewHolder 封装
	// ========================================
	private class ViewHolder extends BaseViewHolder {
		private TextView txtItem;

		private ViewHolder(View view) {
			super(view);
		}

		@Override
		protected void initView() {
			txtItem = findViewById(R.id.txtItem);
		}

		@Override
		protected void initViewEvent() {
			setViewEvent(this);
		}

		@Override
		protected void onRefreshViewContent(ItemInfo object) {
			refreshViewContent(this, object);
		}
	}

	@Override
	protected BaseViewHolder getNewViewHolder(View view) {
		return new ViewHolder(view);
	}
}
