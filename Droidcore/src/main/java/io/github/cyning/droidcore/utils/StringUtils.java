package io.github.cyning.droidcore.utils;

import android.text.TextUtils;
import android.util.Base64;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sssong on 14-03-12.
 */
public class StringUtils {

  /**
   * 大小写转换 大写变小写 小写边大写 只针对key(全部为大写 或全部为小写)
   */
  public static String upLow(String key) {
    if (TextUtils.isEmpty(key)) {
      return null;
    }
    String covString;
    Pattern p = Pattern.compile("[A-Z]+");
    Matcher m1 = p.matcher(key); // 判断是否含有大写字符
    if (m1.find()) {
      covString = key.toLowerCase();// 大变小
    } else {
      covString = key.toUpperCase();
    }
    return covString;
  }

  /**
   * 把带分隔符的字符串 转换为数据
   *
   * @param divisionChar 分隔符
   */
  public static String[] stringAnalytical(String string, String divisionChar) {
    int i = 0;
    StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);
    String[] str = new String[tokenizer.countTokens()];
    while (tokenizer.hasMoreTokens()) {
      str[i] = new String();
      str[i] = tokenizer.nextToken();
      i++;
    }
    return str;
  }

  public static boolean hasText(String str) {
    if (str != null && str.trim().length() > 0) {
      return true;
    }
    return false;
  }

  /**
   * 这个字符为空或者含有空字符串
   */
  public static boolean isEmpty(String str) {
    return !hasText(str);
  }

  public static boolean equals(String str1, String str2) {
    if (str1 == null) {
      return str2 == null;
    } else {
      return str1.equals(str2);
    }
  }

  /**
   * 全角转半角
   *
   * @param input String.
   * @return 半角字符串
   */
  public static String toDBC(String input) {
    char[] c = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == 12288) {
        c[i] = (char) 32;
        continue;
      }
      if (c[i] > 65280 && c[i] < 65375) {
        c[i] = (char) (c[i] - 65248);
      }
    }
    return new String(c);
  }

  /**
   * 半角转全角
   */
  public static String toSBC(String input) {
    char[] c = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == 32) {
        c[i] = (char) 12288;
        continue;
      }
      if (c[i] < 127 && c[i] > 32) {
        c[i] = (char) (c[i] + 65248);
      }
    }
    return new String(c);
  }

  public static boolean equalsOr(String str1, String... strs) {
    for (String mStr : strs) {
      if (equals(str1, mStr)) {
        return true;
      }
    }
    return false;
  }

  public static String deleteHtmlLabel(String content) {
    if (content == null) {
      return content;
    }
    return content.replaceAll("\r", "")
        .replaceAll("\n", "")
        .replaceAll("<(.*?)>", "")
        .replaceAll(" ", "");
  }

  public static String decodeBase64(String data) {
    if (TextUtils.isEmpty(data)) {
      return data;
    }
    final byte[] decodeBase64 = StringUtils.decodeBase64Data(data);
    if (decodeBase64 == null) {
      return data;
    }
    return new String(decodeBase64);
  }

  /**
   * Decodes a base64 String.
   * Unlike Base64.decode() this method does not try to detect and decompress a gzip-compressed
   * input.
   *
   * @param data a base64 encoded String to decode.
   * @return the decoded String.
   */
  private static byte[] decodeBase64Data(String data) {
    byte[] bytes;
    try {
      bytes = data.getBytes("UTF-8");
    } catch (java.io.UnsupportedEncodingException uee) {
      bytes = data.getBytes();
    }

    bytes = Base64.decode(bytes, 0, bytes.length, Base64.DEFAULT);
    return bytes;
  }

  /**
   * 不知道为什么，用Base64.DEFAULT编码，再去解码会失败
   */
  public static String encodeBase64(String data) {
    byte[] bytes;
    try {
      bytes = data.getBytes("UTF-8");
    } catch (java.io.UnsupportedEncodingException uee) {
      bytes = data.getBytes();
    }
    return Base64.encodeToString(bytes, Base64.NO_WRAP);
  }

    public static String subDocId(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
    }

    public static boolean isLongNum(String str){
      try {
         Long.valueOf(str);
      } catch (NumberFormatException e) {
        e.printStackTrace();
        return false;
      }
      return true;

    }
}
