package com.csp.eclipselibrary.com.csp.eclipselibrary.java.util.sourceforge.pinyin4j;

import android.annotation.SuppressLint;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class PinYin4jUtil {
	/**
	 * 获取全拼音(无声调, 全大写, 仅一种音节)
	 * 注: 首字母非中文, 且非英文则追加"#"标示, 例: @abc --> #@abc
	 * @param characters 文字
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getPinyin(String characters) {

		try {
			// 1) 设定拼音的格式(不需要声调，全大写)
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

			// 2) 根据设定好的格式进行转换(重-->[zhong, chong])
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < characters.length(); i++) {
				char ch = characters.charAt(i);
				String chStr = String.valueOf(ch);

				if (chStr.matches("[\u4e00-\u9fff]")) {
					// 当前字符是中文
					String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(ch, format);
					sb.append(pinyin[0]);
				} else if (chStr.matches("[a-zA-Z]")) {
					// 当前字符是英文
					sb.append(chStr.toUpperCase());
				} else {
					// 当前字符是非中文, 且非英文
					if (i == 0) {
						sb.append('#');
					}
					sb.append(ch);
				}
			}

			return sb.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
			throw new RuntimeException("不正确的汉语拼音格式");
		}
	}

	/**
	 * 获取拼音的首字母
	 * @param characters 文字
	 * @return
	 */
	public static char getLetter(String characters) {
		return getPinyin(characters).charAt(0);
	}
}
