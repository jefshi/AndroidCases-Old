package com.csp.cases.activity.network.visit;

import android.content.Context;
import android.util.Log;

import com.csp.cases.activity.network.visit.https.HttpsUtils;
import com.csp.cases.activity.network.visit.retrolfit.GitHubService;
import com.csp.cases.activity.network.visit.retrolfit.RxjavaTrade;
import com.csp.cases.activity.network.visit.retrolfit.hce.CommonRsp;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitNotifyReq;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitNotifyRsp;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitReq;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitRsp;
import com.csp.cases.activity.network.visit.retrolfit.hce.UploadFileListReq;
import com.csp.cases.activity.network.visit.retrolfit.phone.AppUtil;
import com.csp.cases.activity.network.visit.retrolfit.phone.PhoneInfo;
import com.csp.cases.activity.network.visit.retrolfit.phone.PhoneUtil;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        itemInfos.add(new ItemInfo("retrofit 文件上传：已测", "retrofitFile", ""));
        itemInfos.add(new ItemInfo("retrofit 批量文件上传01：已测", "retrofitFiles01", ""));
        itemInfos.add(new ItemInfo("retrofit 批量文件上传02：已测", "retrofitFiles02", ""));
        itemInfos.add(new ItemInfo("retrofit 批量文件上传03：已测（效果同文件上传02）", "retrofitFiles03", ""));
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

    private void retrofitFile() {
        UploadFileListReq request = new UploadFileListReq();
        File file = request.getFiles().get(0);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("bizType", request.getBizType());
        builder.addFormDataPart("appId", request.getAppId());
        builder.addFormDataPart("userId", request.getUserId());
        builder.addFormDataPart("file",
                file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://20.200.22.16:8030/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(supportHttps())
                .build();

        retrofit.create(RxjavaTrade.class)
                .uploadFile("https://20.200.22.16:8030/uploadfile", builder.build())
                .throttleFirst(10, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonRsp commonRsp) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void retrofitFiles01() {
        UploadFileListReq request = new UploadFileListReq();
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("bizType", toRequestBody(request.getBizType()));
        bodyMap.put("appId", toRequestBody(request.getAppId()));
        bodyMap.put("userId", toRequestBody(request.getUserId()));

        List<File> files = request.getFiles();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            bodyMap.put("file" + i + "\";filename=\"" + file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)); // content-type 可以换
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://20.200.22.16:8030/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(supportHttps())
                .build();

        retrofit.create(RxjavaTrade.class)
                .uploadFile("https://20.200.22.16:8030/uploadfile", bodyMap)
                .throttleFirst(10, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonRsp commonRsp) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 传参示例
        // --136a0247-0cd9-43f2-8517-8e7e12605266Content-Disposition: form-data; name="bizType"Content-Transfer-Encoding: binaryContent-Type: text/plain; charset=utf-8Content-Length: 15appSignShareImg
        // --136a0247-0cd9-43f2-8517-8e7e12605266Content-Disposition: form-data; name="appId"Content-Transfer-Encoding: binaryContent-Type: text/plain; charset=utf-8Content-Length: 8zqyl-app
        // --136a0247-0cd9-43f2-8517-8e7e12605266Content-Disposition: form-data; name="file1";filename="Screenshot_20200831_164415_com.zqyl.cloudrent.jpg"Content-Transfer-Encoding: binaryContent-Type: multipart/form-dataContent-Length: 54573������
        // --136a0247-0cd9-43f2-8517-8e7e12605266Content-Disposition: form-data; name="file0";filename="Screenshot_20200831_164536_com.zqyl.cloudrent.jpg"Content-Transfer-Encoding: binaryContent-Type: multipart/form-dataContent-Length: 54674������
        // --136a0247-0cd9-43f2-8517-8e7e12605266Content-Disposition: form-data; name="userId"Content-Transfer-Encoding: binaryContent-Type: text/plain; charset=utf-8Content-Length: 191345186831230042112
        // --136a0247-0cd9-43f2-8517-8e7e12605266--
    }

    private void retrofitFiles02() {
        UploadFileListReq request = new UploadFileListReq();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("bizType", request.getBizType());
        builder.addFormDataPart("appId", request.getAppId());
        builder.addFormDataPart("userId", request.getUserId());

        List<File> files = request.getFiles();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);

            // content-type 可以换
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), body);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://20.200.22.16:8030/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(supportHttps())
                .build();

        retrofit.create(RxjavaTrade.class)
                .uploadFile("https://20.200.22.16:8030/uploadfile", builder.build())
                .throttleFirst(10, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonRsp commonRsp) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 传参示例
        // --30120d48-5291-446f-8511-c1b85ee3bb31Content-Disposition: form-data; name="bizType"Content-Length: 15appSignShareImg
        // --30120d48-5291-446f-8511-c1b85ee3bb31Content-Disposition: form-data; name="appId"Content-Length: 8zqyl-app
        // --30120d48-5291-446f-8511-c1b85ee3bb31Content-Disposition: form-data; name="userId"Content-Length: 191345186831230042112
        // --30120d48-5291-446f-8511-c1b85ee3bb31Content-Disposition: form-data; name="file"; filename="Screenshot_20200831_164536_com.zqyl.cloudrent.jpg"Content-Type: multipart/form-dataContent-Length: 54754������
        // --30120d48-5291-446f-8511-c1b85ee3bb31Content-Disposition: form-data; name="file"; filename="Screenshot_20200831_164415_com.zqyl.cloudrent.jpg"Content-Type: multipart/form-dataContent-Length: 54554������
        // --30120d48-5291-446f-8511-c1b85ee3bb31--
    }

    private void retrofitFiles03() {
        UploadFileListReq request = new UploadFileListReq();

        List<File> fileList = request.getFiles();
        MultipartBody.Part[] files = new MultipartBody.Part[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            // content-type 可以换
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            files[i] = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("bizType", toRequestBody(request.getBizType()));
        map.put("appId", toRequestBody(request.getAppId()));
        map.put("userId", toRequestBody(request.getUserId()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://20.200.22.16:8030/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(supportHttps())
                .build();

        retrofit.create(RxjavaTrade.class)
                .uploadFiles("https://20.200.22.16:8030/uploadfile", map, files)
                .throttleFirst(10, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonRsp commonRsp) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 传参示例
        // --39722ec9-a636-4802-9552-f827a30329fdContent-Disposition: form-data; name="bizType"Content-Transfer-Encoding: binaryContent-Type: text/plain; charset=utf-8Content-Length: 15appSignShareImg
        // --39722ec9-a636-4802-9552-f827a30329fdContent-Disposition: form-data; name="appId"Content-Transfer-Encoding: binaryContent-Type: text/plain; charset=utf-8Content-Length: 8zqyl-app
        // --39722ec9-a636-4802-9552-f827a30329fdContent-Disposition: form-data; name="userId"Content-Transfer-Encoding: binaryContent-Type: text/plain; charset=utf-8Content-Length: 191345186831230042112
        // --39722ec9-a636-4802-9552-f827a30329fdContent-Disposition: form-data; name="file"; filename="Screenshot_20200831_164415_com.zqyl.cloudrent.jpg"Content-Type: multipart/form-dataContent-Length: 54556������
        // --39722ec9-a636-4802-9552-f827a30329fdContent-Disposition: form-data; name="file"; filename="Screenshot_20200831_164536_com.zqyl.cloudrent.jpg"Content-Type: multipart/form-dataContent-Length: 54723������
        // --39722ec9-a636-4802-9552-f827a30329fd--
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}
