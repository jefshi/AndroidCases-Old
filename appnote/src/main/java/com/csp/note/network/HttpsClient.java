package com.csp.note.network;

/**
 * TODO 未整理代码
 */
public class HttpsClient {

//	/**
//	 * 发送https请求共用体
//	 */
//	private static String sendPost(String url, String method, String parame, Map<String, Object> pmap) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
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
//		SSLSocketFactory ssf = JsseX509TrustManager.getSSFactory();
//		try {
//			realUrl = new URL(queryString);
//			conn = (HttpsURLConnection) realUrl.openConnection();
//			conn.setSSLSocketFactory(ssf);
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent",
//					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
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
//	}
//
//	public static void main(String[] args) {
//		String url = "https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&ch=2&tn=56060048_3_pg&wd=%E4%BA%AC%E4%B8%9C%E5%95%86%E5%9F%8E&oq=12%2526lt%253B06%25E9%2593%2581%25E8%25B7%25AF%25E5%25AE%25A2%25E6%2588%25B7%25E6%259C%258D%25E5%258A%25A1%25E4%25B8%25AD%25E5%25BF%2583&rsv_pq=c156671100008ad4&rsv_t=9430RF3GEYXY1xObf4twx2D1dMwNV%2FHcBfP5uFpk9DWZT%2FlhKpN5ylo3rSEAn4w%2Fve%2BWRQ&rqlang=cn&rsv_enter=1&inputT=7315&rsv_sug3=37&rsv_sug1=17&rsv_sug7=100&rsv_sug2=0&prefixsug=%25E4%25BA%25AC%25E4%25B8%259C&rsp=0&rsv_sug4=8769";
//		String methord = "POST";
//		String param = "";
//		Map<String, Object> paramMap = null;
//		String rtnStr;
//		try {
//			rtnStr = sendPost(url, methord, param, paramMap);
//			System.out.println("===" + rtnStr);
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
}