package io.github.cyning.mobilenews.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import io.github.cyning.ShareConfig;

/**
 * 2015(c) NetEase
 *
 * @author Cyning
 * <p>Time 2015.12.21 5:13 PM</p>
 * <p>Desc 类/接口描述</p>
 */

public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        msgApi.registerApp(ShareConfig.WEIXIN_APP_ID);
    }
}

