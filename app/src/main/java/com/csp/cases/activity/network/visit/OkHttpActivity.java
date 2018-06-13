package com.csp.cases.activity.network.visit;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description: [OkHttp]网络访问案例
 * <p>Create Date: 2017/9/12
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class OkHttpActivity extends BaseListActivity {
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private Callback callback;

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo(true, "[OkHttp]方式：get", "getByOkHttp", "官方不推荐多次创建[new OkHttpClient()]"));
		itemInfos.add(new ItemInfo(true, "[OkHttp]方式：post", "postByOkHttp", ""));
		itemInfos.add(new ItemInfo("[OkHttp]方式：异步[GET/POST]", "asyncByOkHttp", "以[GET]为例\n自身[异步访问网络]，需手动往主线程发消息去更新UI"));
		itemInfos.add(new ItemInfo("[OkHttp]方式：[Json]格式请求", "jsonByOkHttp", ""));
		return itemInfos;
	}

	@Override
	public void initBundle() {
		super.initBundle();

		callback = new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				LogCat.e("访问失败：" + "当前线程：" + Thread.currentThread());
				LogCat.printStackTrace(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				ResponseBody body = response.body();
				if (response.isSuccessful() && body != null) {
					LogCat.e("访问成功：", "当前线程：" + Thread.currentThread() + "，返回：" + body.string());
				} else {
					LogCat.e("访问失败：" + "当前线程：" + Thread.currentThread() + "，返回：" + response);
				}
			}
		};
	}

	/**
	 * [OkHttp]方式：GET
	 */
	private void getByOkHttp() throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(NetwokData.STRING_GET_URL)
				.build();
		Call call = client.newCall(request);
		Response response = call.execute();

		ResponseBody body = response.body();
		if (response.isSuccessful() && body != null) {
			LogCat.e(body.string());
		} else {
			LogCat.e("访问失败：" + response);
		}
	}

	/**
	 * [OkHttp]方式：POST
	 */
	private void postByOkHttp() throws IOException {
		FormBody.Builder builder = new FormBody.Builder();
		for (String key : NetwokData.PARAM_MAP.keySet()) {
			builder.add(key, NetwokData.PARAM_MAP.get(key));
		}

		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = builder.build();
		Request request = new Request.Builder()
				.url(NetwokData.STRING_URL)
				.post(requestBody)
				.build();
		Call call = client.newCall(request);
		Response response = call.execute();

		ResponseBody body = response.body();
		if (response.isSuccessful() && body != null) {
			LogCat.e(body.string());
		} else {
			LogCat.e("访问失败：" + response);
		}
	}

	/**
	 * [OkHttp]方式：异步[GET/POST]
	 */
	private void asyncByOkHttp() throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(NetwokData.STRING_GET_URL)
				.build();
		Call call = client.newCall(request);
		call.enqueue(callback);
	}

	/**
	 * [OkHttp]方式：[Json]格式请求
	 */
	private void jsonByOkHttp() throws IOException {
		OkHttpClient client = new OkHttpClient();
		RequestBody requestBody = RequestBody.create(JSON, NetwokData.PARAM_JSON);
		Request request = new Request.Builder()
				.url(NetwokData.STRING_GET_URL)
				.post(requestBody)
				.build();
		Call call = client.newCall(request);
		call.enqueue(callback);
	}
}
