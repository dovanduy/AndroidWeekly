package io.github.cyning.droidcore.utils;

/**
 * Author Cyning
 * Date   2015.08.25
 * Time   下午7:26
 * Desc   <p>类/接口描述</p>
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * MD5 算法
*/
public class
Md5Utils {

    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
                                                "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };



    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为10进制数字
    private static String byteToNumber(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToNum(bByte[i]));
        }
        return sBuffer.toString();
    }
    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static int encrypString(String str){
        String resultString = "";
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToNumber(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            resultString = System.currentTimeMillis()+"";
        }

        if (resultString.length() > 8){
            return Integer.valueOf(resultString.substring(0,8));
        }else {
            return  Integer.valueOf(resultString);
        }

    }


    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
//            resultString = byteToString(md.digest(strObj.getBytes()));
            resultString = byteToNumber(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * @param str
     *
     * @return
     */
    public static String parseStrToMd5L32(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     * @param str
     *
     * @return
     */
    public static String parseStrToMd5U32(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }

    /**
     * @param str
     *
     * @return
     */
    public static String parseStrToMd5U16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase().substring(8, 24);
        }
        return reStr;
    }

    /**
     * @param str
     *
     * @return
     */
    public static String parseStrToMd5L16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }

    public static String md5(String input) {
        String result = input;
        if(input != null && !"".equals(input)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(input.getBytes());
                BigInteger e = new BigInteger(1, md.digest());

                for(result = e.toString(16); result.length() < 32; result = "0" + result) {
                    ;
                }
            } catch (NoSuchAlgorithmException var4) {
                var4.printStackTrace();
            }
        }

        return result;
    }


}
