package io.github.cyning.droidcore.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author cyning
 * @Date 2015.07.13
 * @Time 下午4:42
 * @desc <p>类/接口描述</p>
 */
public class ResUtils {

    private static  Context sContext;

    public static  void init(Context mContext){
        if (sContext == null) {
            if (mContext != null) {
                sContext = mContext.getApplicationContext();
            }else{
                throw  new  NullPointerException("mContext is null");
            }
        }
    }

    private static Object checkNull(){
        if (sContext ==null){
            throw  new  NullPointerException("ResUtils.init is not called");
        }

        return  null;
    }
    public  static String  getString(Context mContext,int resId){
        if (mContext == null) {
            mContext = sContext;
        }
        return  mContext.getResources().getString(resId);
    }
    public  static Drawable getDrawable(Context mContext,int resId){
        if (mContext == null) {
            mContext = sContext;
        }
        return  mContext.getResources().getDrawable(resId);
    }


    public static  int  getColor(Context mContext,int resId){
        return  mContext.getResources().getColor(resId);
    }



}
