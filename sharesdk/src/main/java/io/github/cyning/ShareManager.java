package io.github.cyning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.github.cyning.share.R;


/**
 * @author Cyning
 *         <p>Time 2015.11.12 4:22 PM</p>
 *         <p>Desc 类/接口描述</p>
 */
public class ShareManager {

    private static ShareManager mInstance = null;

    /*****************************************  ***********************************************/

    byte[] logo = null;
    Bitmap logoBitMap = null;

    private ShareManager() {
    }

    public static ShareManager getInstance() {
        if (mInstance == null) {
            synchronized (ShareManager.class) {
                if (mInstance == null) {
                    mInstance = new ShareManager();
                }
            }
        }
        return mInstance;
    }

    public byte[] getLogo(Context mContext) {
        if (logo == null) {
            if (logoBitMap == null) {
                logoBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share);
            }

            logo = Util.bmpToByteArray(logoBitMap, true);
        }
        return logo;
    }

    public Bitmap getLogoBmp(Context mContext) {
        if (logoBitMap == null) {
            logoBitMap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share);
        }
        return logoBitMap;
    }
    /*****************************************  ***********************************************/

}
