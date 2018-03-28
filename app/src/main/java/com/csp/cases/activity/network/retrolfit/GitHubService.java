package com.csp.cases.activity.network.retrolfit;

import com.csp.cases.activity.network.NetwokData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
  @GET("LoginServlet")
  Call<NetwokData.User> listRepos(@Query("name") String name, @Query("password") String password);

  // 等价于
  // @HTTP(method = "GET", path = "LoginServlet?password=password&name=admin", hasBody = false)
  // Call<NetwokData.User> listRepos();
}