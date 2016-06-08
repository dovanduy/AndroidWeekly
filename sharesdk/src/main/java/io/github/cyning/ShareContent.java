package io.github.cyning;

import java.io.Serializable;

/**
 * @author Cyning
 * <p>Time 2015.11.12 4:41 PM</p>
 * <p>Desc 分享的内容</p>
 */

public class ShareContent implements Serializable {

    private String mTitle;

    private String mDes;

    private String mUrl;

    private String from;

    public String getTitle() {
        return mTitle;
    }

    public ShareContent setTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public String getDes() {
        return mDes;
    }

    public ShareContent setDes(String mDes) {
        this.mDes = mDes;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public ShareContent setUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    @Override
    public String toString() {
        return "ShareContent{" +
               "mTitle='" + mTitle + '\'' +
               ", mDes='" + mDes + '\'' +
               ", mUrl='" + mUrl + '\'' +
               '}';
    }
}
