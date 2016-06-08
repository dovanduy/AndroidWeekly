package io.github.cyning.droidcore.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Cyning
 * @since 2016.05.19
 * Time    18:39
 * Desc    <p>类/接口描述</p>
 */
public class DateUtils {

    static SimpleDateFormat MMDD =  new SimpleDateFormat( "yy-MM-dd" );

    public static String toMMDD(long time){

       return MMDD.format(time);
    }

    public static long strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
       return strtodate.getTime();
    }
}
