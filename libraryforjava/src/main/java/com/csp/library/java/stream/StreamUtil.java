package com.csp.library.java.stream;

import com.csp.library.java.constant.TextCoding;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Description: 流操作工具类
 * <p>Create Date: 2017/9/11
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since JavaLibrary 1.0.0
 */
@SuppressWarnings({"unused", "UnusedAssignment"})
public class StreamUtil {
	private final static int MIN_BUFFER_LENGTH = 8192; // 8 KB

	/**
	 * 输入流 -> 字符串
	 *
	 * @param is 输入流
	 * @return String 字符串
	 *
	 * @throws IOException
	 */
	public static String getString(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, TextCoding.UTF_8));

		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	/**
	 * 输入流 -> byte[]
	 *
	 * @param is 输入流
	 * @return byte[]
	 *
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[MIN_BUFFER_LENGTH];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}
}
