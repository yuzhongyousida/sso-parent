package org.example.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author wangteng05
 * @description: JedisUtil类
 * @date 2021/10/28 16:55
 */
public class StringUtils {

	/**
	 * 字符串空判断
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	/**
	 * 判断制定字符串是否有内容（即：不为空且至少有一个非空白字符）
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str) {
		return (hasLength(str) && containsText(str));
	}



	/**
	 * 指定字符串按照制定分隔符分隔成字符串数组
	 * @param str  指定字符串
	 * @param delimiters  指定分隔符
	 * @param trimTokens  是否对前后空白进行trim
	 * @param ignoreEmptyTokens  是否忽略分隔后的空字符串
	 * @return
	 */
	public static String[] tokenizeToStringArray(String str,
												 String delimiters,
												 boolean trimTokens,
												 boolean ignoreEmptyTokens) {
		if (str == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * 字符列表转换为字符数组
	 * @param collection
	 * @return
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * 判断指定字符串是否含有文本内容（即不是全为空白符）
	 * @param str
	 * @return
	 */
	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
