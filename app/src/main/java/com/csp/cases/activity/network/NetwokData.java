package com.csp.cases.activity.network;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Description: 网络访问通过参数
 * <p>Create Date: 2017/9/12
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class NetwokData {
	public final static String ROOT_URL = "http://20.5.172.225:8080/MyDynamicWeb";
	public final static String STRING_URL = ROOT_URL + "/LoginServlet";
	public final static String IMGAGE_URL = ROOT_URL + "/header.png";

	public final static String HTTPS_URL = "https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&ch=2&tn=56060048_3_pg&wd=%E4%BA%AC%E4%B8%9C%E5%95%86%E5%9F%8E&oq=12%2526lt%253B06%25E9%2593%2581%25E8%25B7%25AF%25E5%25AE%25A2%25E6%2588%25B7%25E6%259C%258D%25E5%258A%25A1%25E4%25B8%25AD%25E5%25BF%2583&rsv_pq=c156671100008ad4&rsv_t=9430RF3GEYXY1xObf4twx2D1dMwNV%2FHcBfP5uFpk9DWZT%2FlhKpN5ylo3rSEAn4w%2Fve%2BWRQ&rqlang=cn&rsv_enter=1&inputT=7315&rsv_sug3=37&rsv_sug1=17&rsv_sug7=100&rsv_sug2=0&prefixsug=%25E4%25BA%25AC%25E4%25B8%259C&rsp=0&rsv_sug4=8769";

	// 请求参数
	public static User user;
	public static HashMap<String, String> PARAM_MAP;
	public static String PARAM_JSON;

	// [GET]方式URL
	public static String STRING_GET_URL;

	public static void init() {
		// 请求参数
		user = new User();
		user.setName("admin");
		user.setPassword("password");

		PARAM_MAP = new HashMap<>();
		PARAM_MAP.put("name", user.getName());
		PARAM_MAP.put("password", user.getPassword());

		PARAM_JSON = new Gson().toJson(user);

		// [GET]方式URL
		StringBuilder builder = new StringBuilder();
		builder.append(STRING_URL + '?');
		for (String key : PARAM_MAP.keySet()) {
			builder.append(key + '=').append(PARAM_MAP.get(key) + '&');
		}
		builder.deleteCharAt(builder.length() - 1);
		STRING_GET_URL = builder.toString();
	}

	/**
	 * 请求参数
	 */
	@SuppressWarnings("unused")
	public static class User {
		private String name;
		private String password;
		private String email;
		private String phone;
		private String result;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + '{' +
					"'" + name + '\'' +
					", '" + password + '\'' +
					", '" + email + '\'' +
					", '" + phone + '\'' +
					", '" + result + '\'' +
					'}';
		}
	}
}
