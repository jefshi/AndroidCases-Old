package com.csp.cases.activity.network.retrolfit;

import com.csp.cases.activity.network.NetwokData;
import com.csp.cases.activity.network.retrolfit.hce.SdkInitNotifyReq;
import com.csp.cases.activity.network.retrolfit.hce.SdkInitNotifyRsp;
import com.csp.cases.activity.network.retrolfit.hce.SdkInitReq;
import com.csp.cases.activity.network.retrolfit.hce.SdkInitRsp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RxjavaTrade {
	@GET("LoginServlet")
	Observable<NetwokData.User> listRepos(@Query("name") String name, @Query("password") String password);

	@POST("/hce")
	Observable<SdkInitRsp> sdkInitialize(@Body SdkInitReq request);

	@POST("/hce")
	Observable<SdkInitNotifyRsp> sdkNotify(@Body SdkInitNotifyReq request);
}