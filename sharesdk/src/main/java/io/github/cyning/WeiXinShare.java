package io.github.cyning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import io.github.cyning.droidcore.utils.StringUtils;

/**
 *
 * @author Cyning
 *         <p>Time 2015.11.12 5:14 PM</p>
 *         <p>Desc 微信的实现分享的内容</p>
 */

public class WeiXinShare implements IShareClient {

    public static final String DOWNLOAD_URL = "http://weixin.qq.com/";
    IWXAPI weixin = null;

    private boolean isTimeline = false;

    public WeiXinShare(boolean mIsTimeline) {
        isTimeline = mIsTimeline;
    }

    public WeiXinShare setIsTimeline(boolean mIsTimeline) {
        isTimeline = mIsTimeline;
        return this;
    }

    public boolean isTimeline() {
        return isTimeline;
    }

    @Override
    public void share(Activity mActivity, ShareContent mContent) {
        try {

            weixin = WXAPIFactory.createWXAPI(mActivity, ShareConfig.WEIXIN_APP_ID, true);
            weixin.registerApp(ShareConfig.WEIXIN_APP_ID);

            WXMediaMessage msg = null;
            String url = mContent.getUrl();
            WXMediaMessage.IMediaObject obj = null;
            if (StringUtils.hasText(url)) {
                obj = new WXWebpageObject(mContent.getUrl());
            } else {
                obj = new WXTextObject(mContent.getDes());
            }
            msg = new WXMediaMessage(obj);
            msg.title = mContent.getTitle();
            msg.description = mContent.getDes();
            if (StringUtils.isEmpty(mContent.getUrl())) {
                msg.thumbData = ShareManager.getInstance()
                        .getLogo(mActivity);
            } else {
//                msg.thumbData = Util.getHtmlByteArray(mContent.getUrl());

                msg.thumbData
                 = ShareManager.getInstance()
                        .getLogo(mActivity);
            }
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            if (isTimeline) {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //                AnchorUtil.setEvent(mActivity, isStockShare ? AnchorUtil.EVENT_STOCK_SHARE : AnchorUtil.EVENT_NEWS_SHARE, "微信朋友圈");
            } else {
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //                AnchorUtil.setEvent(mActivity, isStockShare ? AnchorUtil.EVENT_STOCK_SHARE : AnchorUtil.EVENT_NEWS_SHARE, "微信好友");
            }
            //            AnchorUtil.setEvent(mActivity, isStockShare ? AnchorUtil.EVENT_STOCK_SHARE : AnchorUtil.EVENT_NEWS_SHARE, "全部分享次数");
            weixin.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mActivity, "分享失败", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = image.getHeight() /4;//这里设置高度为800f
        float ww = image.getWidth() /4;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>1024) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }



    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static void appNotInstalled(final Context mContext, int contentId, final String downloadUrl) {

    }

    @Override
    public boolean isInstalled(Activity mActivity) {
        weixin = WXAPIFactory.createWXAPI(mActivity, ShareConfig.WEIXIN_APP_ID, true);
        return weixin.isWXAppInstalled();
    }

    @Override
    public void installAPP(Activity mActivity) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(DOWNLOAD_URL));
        mActivity.startActivity(i);
    }

    @Override
    public int getTitleId() {
        return  0 ;
    }
}
