package com.csp.cases.activity.network.visit;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.java.string.StringUtil;
import com.csp.utils.android.log.LogCat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshp on 2018/4/18.
 */

public class NetworkVisitActivity extends BaseGridActivity {

    @Override
    public void initBundle() {
        super.initBundle();

        NetwokData.init();
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(new ItemInfo(true, "[HttpURLConnection]方式：GET", "getByHttpURLConnection", ""));
        itemInfos.add(new ItemInfo(true, "[HttpURLConnection]方式：POST", "postByHttpURLConnection", ""));
        itemInfos.add(new ItemInfo(true, "[HttpsURLConnection]方式：POST", "postByHttpsURLConnection", ""));
        itemInfos.add(new ItemInfo(true, "[HttpClient]方式：GET", "getByHttpClient", "[Android 6.0]后废弃\n启用请在[build.gradele]中添加[useLibrary 'org.apache.http.legacy']"));
        itemInfos.add(new ItemInfo(true, "[HttpClient]方式：POST", "postByHttpClient", "[Android 6.0]后废弃\n启用请在[build.gradele]中添加[useLibrary 'org.apache.http.legacy']"));
        itemInfos.add(new ItemInfo("[Volley]方式", VolleyActivity.class, "[Volley]网络访问案例"));
        itemInfos.add(new ItemInfo("[OkHttp]方式", OkHttpActivity.class, "[OkHttp]网络访问案例\n官方不推荐多次创建[new OkHttpClient()]"));
        itemInfos.add(new ItemInfo("[Retrofit]方式", RetrofitActivity.class, ""));
        return itemInfos;
    }

    /**
     * [HttpURLConnection]方式：GET
     */
    private void getByHttpURLConnection() throws IOException {
        // 网络访问
        URL url = new URL(NetwokData.STRING_GET_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true); // 是否连接遵循重定向
        connection.setUseCaches(false);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
        connection.setRequestProperty("Content-Length", "0");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();

        // 访问结果
        int state = connection.getResponseCode();
        if (state == 200) {
            InputStream is = connection.getInputStream();
            LogCat.e("访问成功：", StringUtil.toString(is));
        } else {
            LogCat.e("访问失败：响应码：" + state + "，URL：" + NetwokData.STRING_URL);
        }

        connection.disconnect();
    }

    /**
     * [HttpURLConnection]方式：POST
     */
    private void postByHttpURLConnection() throws IOException {
        // 请求参数
        StringBuilder entity = new StringBuilder();
        for (String key : NetwokData.PARAM_MAP.keySet()) {
            entity.append(key + '=').append(NetwokData.PARAM_MAP.get(key) + '&');
        }
        entity.deleteCharAt(entity.length() - 1);
        byte[] data = entity.toString().getBytes();

        // 网络访问
        URL url = new URL(NetwokData.STRING_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true); // 是否连接遵循重定向
        connection.setUseCaches(false);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", data.length + "");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();

        OutputStream os = connection.getOutputStream();
        os.write(data);
        os.flush();
        os.close();

        // 访问结果
        int state = connection.getResponseCode();
        if (state == 200) {
            InputStream is = connection.getInputStream();
            LogCat.e("访问成功：", StringUtil.toString(is));
        } else {
            LogCat.e("访问失败：响应码：" + state + "，URL：" + NetwokData.STRING_URL);
        }

        connection.disconnect();
    }

    private void postByHttpsURLConnection() throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        // TODO HTTPS
//		URL url = new URL(NetwokData.HTTPS_URL);
//		SSLSocketFactory ssf = JsseX509TrustManager.getSSFactory();
//		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//		conn.setSSLSocketFactory(ssf);
//		conn.setRequestProperty("accept", "*/*");
//		conn.setRequestProperty("connection", "Keep-Alive");
//		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//
//		// 请求结果
//		PrintWriter out = null;
//		BufferedReader in = null;
//		String result = "";
//		URL realUrl;
//		HttpsURLConnection conn;
//		//查询地址
//		String queryString = url;
//		//请求参数获取
//		String postpar = "";
//		//字符串请求参数
//		if (parame != null) {
//			postpar = parame;
//		}
//		// map格式的请求参数
//		if (pmap != null) {
//			StringBuffer mstr = new StringBuffer();
//			for (String str : pmap.keySet()) {
//				String val = (String) pmap.get(str);
//				try {
//					val = URLEncoder.encode(val, "UTF-8");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//				mstr.append(str + "=" + val + "&");
//			}
//			// 最终参数
//			postpar = mstr.toString();
//			int lasts = postpar.lastIndexOf("&");
//			postpar = postpar.substring(0, lasts);
//		}
//		if (method.toUpperCase().equals("GET")) {
//			queryString += "?" + postpar;
//		}
//
//		try {
//			if (method.toUpperCase().equals("POST")) {
//				conn.setDoOutput(true);
//				conn.setDoInput(true);
//				conn.setUseCaches(false);
//				out = new PrintWriter(conn.getOutputStream());
//				out.print(postpar);
//				out.flush();
//			} else {
//				conn.connect();
//			}
//			in = new BufferedReader(
//					new InputStreamReader(conn.getInputStream(), "utf-8"));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += line;
//			}
//		} finally {
//			try {
//				if (out != null) {
//					out.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return result;
    }

    /**
     * [HttpClient]方式：GET
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    private void getByHttpClient() throws IOException {
        // 网络访问
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(NetwokData.STRING_GET_URL);
        HttpResponse response = client.execute(get);

        // 访问结果
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            LogCat.e("[GET]方式访问成功");

            HttpEntity result = response.getEntity();
            LogCat.e(EntityUtils.toString(result));
        } else {
            LogCat.e("访问失败：响应码：" + status + "，URL：" + NetwokData.STRING_URL);
        }
    }

    /**
     * [HttpClient]方式：POST
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    private void postByHttpClient() throws IOException {
        // 请求参数
        List<NameValuePair> pairs = new ArrayList<>();
        for (String key : NetwokData.PARAM_MAP.keySet()) {
            pairs.add(new BasicNameValuePair(key, NetwokData.PARAM_MAP.get(key)));
        }

        // 网络访问
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(NetwokData.STRING_URL);
        HttpEntity entity = new UrlEncodedFormEntity(pairs);
        post.setEntity(entity);
        HttpResponse response = client.execute(post);

        // 访问结果
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
            LogCat.e("[POST]方式访问成功");

            HttpEntity result = response.getEntity();
            LogCat.e(EntityUtils.toString(result));
        } else {
            LogCat.e("访问失败：响应码：" + status + "，URL：" + NetwokData.STRING_URL);
        }
    }
}
