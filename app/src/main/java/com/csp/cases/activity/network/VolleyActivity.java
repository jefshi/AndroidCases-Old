package com.csp.cases.activity.network;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csp.cases.R;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.util.log.LogCat;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: [Volley]网络访问案例
 * <p>Create Date: 2017/9/12
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class VolleyActivity extends BaseListActivity {
	private Response.Listener<String> stringListener;
	private Response.Listener<Bitmap> bitmapListener;
	private Response.ErrorListener errorListener;

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("[Volley]方式：GET", "getByVolley", "基于[HttpClient]方式\n[Volley]自身[异步访问网络，主线程更新UI]"));
		itemInfos.add(new ItemInfo("[Volley]方式：POST", "postByVolley", "基于[HttpClient]方式\n[Volley]自身[异步访问网络，主线程更新UI]"));
		itemInfos.add(new ItemInfo("[Volley]方式：ImageRequest", "volleyByImageRequest", "自身[异步访问网络，主线程更新UI]"));
		itemInfos.add(new ItemInfo("[Volley]方式：Imageloader", "volleyByImageloader", "必须在主线程中执行，自身[异步访问网络]"));
		itemInfos.add(new ItemInfo("[Volley]方式：自定义[Request]", "volleyByCustomRequest", "自身[异步访问网络，主线程更新UI]"));
		return itemInfos;
	}

	@Override
	public void initBundle() {
		super.initBundle();

		stringListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				logError("访问成功：", response);
			}
		};

		bitmapListener = new Response.Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				logError("图片加载成功");
				imgItem.setImageBitmap(response);
			}
		};

		errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				LogCat.printStackTrace(error);
			}
		};
	}

	/**
	 * [Volley]方式：GET
	 */
	@Deprecated
	private void getByVolley() {
		// 网络访问
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest request = new StringRequest(
				Request.Method.GET,
				NetwokData.STRING_GET_URL,
				stringListener,
				errorListener);
		queue.add(request);
	}

	/**
	 * [Volley]方式：POST
	 */
	@Deprecated
	private void postByVolley() {
		// 网络访问
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest request = new StringRequest(
				Request.Method.POST,
				NetwokData.STRING_URL,
				stringListener,
				errorListener) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return NetwokData.PARAM_MAP;
			}
		};
		queue.add(request);
	}

	/**
	 * [Volley]方式：ImageRequest
	 */
	@Deprecated
	private void volleyByImageRequest() {
		// 网络访问
		RequestQueue queue = Volley.newRequestQueue(this);
		ImageRequest request = new ImageRequest(
				NetwokData.IMGAGE_URL,
				bitmapListener,
				100,
				200,
				Bitmap.Config.ARGB_8888,
				errorListener);
		queue.add(request);
	}

	/**
	 * [Volley]方式：Imageloader
	 */
	@Deprecated
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private void volleyByImageloader() {
		ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
			int cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;
			LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getHeight() * bitmap.getRowBytes();
				}
			};

			@Override
			public Bitmap getBitmap(String key) {
				return cache.get(key);
			}

			@Override
			public void putBitmap(String key, Bitmap bitmap) {
				cache.put(key, bitmap);
			}
		};

		// 网络访问
		RequestQueue queue = Volley.newRequestQueue(this);
		ImageLoader loader = new ImageLoader(
				queue,
				imageCache);

		// 方法01: 设置默认图片, 失败图片
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(
				imgItem,
				R.mipmap.item_card,
				R.mipmap.ic_launcher);
		loader.get(NetwokData.IMGAGE_URL, listener);

		// 方法02:
		// ((NetworkImageView) imgItem).setImageUrl(IMGAGE_URL, loader);
	}

	/**
	 * [Volley]方式：自定义[Request]
	 */
	@Deprecated
	private void volleyByCustomRequest() {
		// 请求参数
		StringBuilder urlPath = new StringBuilder();
		urlPath.append(NetwokData.STRING_URL + '?');
		for (String key : NetwokData.PARAM_MAP.keySet()) {
			urlPath.append(key + '=').append(NetwokData.PARAM_MAP.get(key) + '&');
		}
		urlPath.deleteCharAt(urlPath.length() - 1);

		// 网络访问
		RequestQueue queue = Volley.newRequestQueue(this);
		UserRequest request = new UserRequest(
				Request.Method.GET,
				urlPath.toString(),
				new Response.Listener<NetwokData.User>() {

					@Override
					public void onResponse(NetwokData.User response) {
						logError(response);
					}
				},
				errorListener);
		queue.add(request);
	}

	/**
	 * 自定义[Request]
	 */
	private static class UserRequest extends Request<NetwokData.User> {
		private Response.Listener<NetwokData.User> mListener;

		public UserRequest(int method, String url, Response.Listener<NetwokData.User> listener, Response.ErrorListener errorListener) {
			super(method, url, errorListener);
			mListener = listener;
		}

		@Override
		protected Response<NetwokData.User> parseNetworkResponse(NetworkResponse response) {
			try {
				String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

				LogCat.e(response.headers);
				LogCat.e(HttpHeaderParser.parseCharset(response.headers));

				Gson gson = new Gson();
				NetwokData.User nba = gson.fromJson(jsonStr, NetwokData.User.class);
				return Response.success(
						nba,
						HttpHeaderParser.parseCacheHeaders(response));
			} catch (UnsupportedEncodingException e) {
				return Response.error(new ParseError(e));
			}
		}

		@Override
		protected void deliverResponse(NetwokData.User user) {
			mListener.onResponse(user);
		}
	}
}
