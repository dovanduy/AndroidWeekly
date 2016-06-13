package io.github.cyning.mobilenews;

import io.github.cyning.droidcore.utils.Md5Utils;

/**
 * @author Cyning
 * @since 2016.05.11
 * Time    10:18
 * Desc    <p>类/接口描述</p>
 */
public class Key {

   public static final String  xKey = "hoOc0q3Bn4NIU9YObBQY0VsL";

    public static  String getSign(){
        long time = System.currentTimeMillis();
        return Md5Utils.parseStrToMd5L32(time+xKey)+","+time;
    }
}
