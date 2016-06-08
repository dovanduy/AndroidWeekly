package io.github.cyning;

import android.app.Activity;

/**
 * 2015(c) NetEase
 * @author Cyning
 *         <p>Time 2015.11.12 5:04 PM</p>
 *         <p>Desc 不同的分享渠道的接口</p>
 */

public interface IShareClient {

    public void share(Activity mActivity, ShareContent mContent);

    public boolean isInstalled(Activity mActivity);

    public void installAPP(Activity mActivity);

    public int getTitleId();
}
