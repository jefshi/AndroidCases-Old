package com.csp.cases.activity.network.visit;

import android.content.Context;
import android.util.Log;

import com.csp.cases.activity.network.visit.https.HttpsUtils;
import com.csp.cases.activity.network.visit.retrolfit.GitHubService;
import com.csp.cases.activity.network.visit.retrolfit.RxjavaTrade;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitNotifyReq;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitNotifyRsp;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitReq;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitRsp;
import com.csp.cases.activity.network.visit.retrolfit.phone.AppUtil;
import com.csp.cases.activity.network.visit.retrolfit.phone.PhoneInfo;
import com.csp.cases.activity.network.visit.retrolfit.phone.PhoneUtil;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
public class RetrofitActivity extends BaseListActivity {
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private Callback<NetwokData.User> callback;

	@Override
	public List<ItemInfo> getItemInfos() {
		// TODO HCE 的数据清除

		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("retrolfit", "retrolfit", ""));
		itemInfos.add(new ItemInfo("rxjava + retrolfit", "rxjavaRetrolfit", ""));
		itemInfos.add(new ItemInfo("retrofitDemo(HTTP + POST)", "retrofitDemo", ""));
		return itemInfos;
	}

	@Override
	public void initBundle() {
		super.initBundle();

		callback = new Callback<NetwokData.User>() {
			@Override
			public void onResponse(Call<NetwokData.User> call, Response<NetwokData.User> response) {
				NetwokData.User body = response.body();
				if (response.isSuccessful() && body != null) {
					LogCat.e("访问成功：", "当前线程：" + Thread.currentThread() + "，返回：" + body.toString());
				} else {
					LogCat.e("访问失败：" + "当前线程：" + Thread.currentThread() + "，返回：" + response);
				}
			}

			@Override
			public void onFailure(Call<NetwokData.User> call, Throwable throwable) {
				LogCat.e("访问失败：" + "当前线程：" + Thread.currentThread());
				LogCat.printStackTrace(new Exception(throwable));
			}
		};
	}

	private void retrolfit() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://20.5.172.225:8080/MyDynamicWeb/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GitHubService service = retrofit.create(GitHubService.class);
		Call<NetwokData.User> repos = service.listRepos("admin", "pass");

		repos.enqueue(callback);
	}

	private void rxjavaRetrolfit() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://20.5.172.225:8080/MyDynamicWeb/")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();

		final RxjavaTrade trade = retrofit.create(RxjavaTrade.class);
		trade.listRepos("admin", "pass")
				.subscribeOn(Schedulers.io())
				.flatMap(new Function<NetwokData.User, ObservableSource<NetwokData.User>>() {
					@Override
					public ObservableSource<NetwokData.User> apply(@NonNull NetwokData.User user) throws Exception {
						Log.e("访问成功：", "当前线程：" + Thread.currentThread() + "，返回：" + user.toString());
						return trade.listRepos(user.getName() + "02", user.getPassword() + "02");
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<NetwokData.User>() {
					private Disposable disposable;

					@Override
					public void onSubscribe(@NonNull Disposable disposable) {
						this.disposable = disposable;
					}

					@Override
					public void onNext(@NonNull NetwokData.User user) {
						Log.e("访问成功：", "当前线程：" + Thread.currentThread() + "，返回：" + user.toString());
						txtItem.setText("访问成功：" + "当前线程：" + Thread.currentThread() + "，返回：" + user.toString());
					}

					@Override
					public void onError(@NonNull Throwable throwable) {
						LogCat.printStackTrace(new Exception(throwable));
					}

					@Override
					public void onComplete() {
						disposable.dispose();
					}
				});

		// repos.enqueue(callback);
	}

	public void retrofitDemo() {
		Context context = this;
		PhoneInfo phoneInfo = new PhoneUtil().getPhoneInfo(this);

		SdkInitReq request = new SdkInitReq();
		request.setTxnType("10009901");
		request.setUserId("hce");
		request.setIsRoot("0");
		request.setDeviceModel(phoneInfo.getPhoneHardwareInfo().getTerminalType());
		request.setDeviceSn(phoneInfo.getPhoneHardwareInfo().getTerminalSn());
		request.setOsType(phoneInfo.getPhoneSoftwareInfo().getMobileOsType());
		request.setOsVersion(phoneInfo.getPhoneSoftwareInfo().getMobileOsVersion());
		request.setDeviceId(phoneInfo.getPhoneHardwareInfo().getAndroidId());
		request.setDeviceUniqId(PhoneUtil.getUUID(context));
		request.setMac(phoneInfo.getPhoneHardwareInfo().getMac());
		request.setImei(phoneInfo.getPhoneHardwareInfo().getImei());
		request.setWalletName(phoneInfo.getPhoneSoftwareInfo().getMobileAppName());
		request.setWalletSignature(phoneInfo.getPhoneSoftwareInfo().getMobileAppSign());
		request.setWalletVersion(phoneInfo.getPhoneSoftwareInfo().getMobileAppVersion());
		request.setIsNFC("0");
		request.setIsSeFlag("0");
		request.setSeId(null);
		request.setGpsLocation("");
		request.setAccessPin("JwaxV7HzXSwp7k5a+XC8RqO31YIdlx4QF8py3QdzeLPqRYKVxvPzcNnMVFEalWm2");
		request.setDeviceHash(AppUtil.getFingerprint(context));





		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://20.200.22.16:8030/")
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(supportHttps())
				.build();





		final RxjavaTrade trade = retrofit.create(RxjavaTrade.class);

		trade.sdkInitialize(request)
				.subscribeOn(Schedulers.io())
				.flatMap(new Function<SdkInitRsp, ObservableSource<SdkInitNotifyRsp>>() {
					@Override
					public ObservableSource<SdkInitNotifyRsp> apply(@NonNull SdkInitRsp sdkInitRsp) throws Exception {
						Log.e("访问成功：", "当前线程：" + Thread.currentThread() + "，返回：" + sdkInitRsp.toString());

						SdkInitNotifyReq request = new SdkInitNotifyReq();
						request.setTxnType("10009902");
						request.setMpaId(sdkInitRsp.getMpaId());
						request.setUserId(sdkInitRsp.getUserId());
						request.setNotifyRlt("1");

						return trade.sdkNotify(request);
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<SdkInitNotifyRsp>() {
					private Disposable disposable;

					@Override
					public void onSubscribe(@NonNull Disposable disposable) {
						this.disposable = disposable;
					}

					@Override
					public void onNext(@NonNull SdkInitNotifyRsp response) {
						Log.e("访问成功：", "当前线程：" + Thread.currentThread() + "，返回：" + response.toString());
					}

					@Override
					public void onError(@NonNull Throwable throwable) {
						LogCat.printStackTrace(new Exception(throwable));
					}

					@Override
					public void onComplete() {
						disposable.dispose();
					}
				});
	}


	private OkHttpClient supportHttps() {
		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
		OkHttpClient client = new OkHttpClient.Builder()
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				})
				.build();
		return client;
	}
}
