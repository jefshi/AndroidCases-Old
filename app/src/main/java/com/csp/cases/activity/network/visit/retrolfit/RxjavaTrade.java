package com.csp.cases.activity.network.visit.retrolfit;

import com.csp.cases.activity.network.visit.NetwokData;
import com.csp.cases.activity.network.visit.retrolfit.hce.CommonRsp;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitNotifyReq;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitNotifyRsp;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitReq;
import com.csp.cases.activity.network.visit.retrolfit.hce.SdkInitRsp;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RxjavaTrade {
	@GET("LoginServlet")
	Observable<NetwokData.User> listRepos(@Query("name") String name, @Query("password") String password);

	@POST("/hce")
	Observable<SdkInitRsp> sdkInitialize(@Body SdkInitReq request);

	@POST("/hce")
	Observable<SdkInitNotifyRsp> sdkNotify(@Body SdkInitNotifyReq request);

	/**
	 * 上传文件/批量上传文件
	 */
	@POST
	Observable<CommonRsp> uploadFile(@Url String url, @Body RequestBody body);

	/**
	 * 上传文件/批量上传文件
	 */
	@POST
	Observable<CommonRsp> uploadFile(@Url String url, @PartMap Map<String, RequestBody> params);

	/**
	 * 批量上传文件
	 */
	@POST
	@Multipart
	Observable<CommonRsp> uploadFiles(@Url String url, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part[] files);
}