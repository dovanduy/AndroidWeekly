package io.github.cyning.mobilenews.hotarticle.model;

import java.io.Serializable;

/**
 * @author Cyning
 * @since 2016.03.12
 * Time    2:01 AM
 * Desc    <p>类/接口描述</p>
 */

public class HotArticleTabSpec implements Serializable{
    private String tabName ;
    private String tabId;

    public HotArticleTabSpec(String tabName, String tabId) {
        this.tabName = tabName;
        this.tabId = tabId;
    }

    public String getTabName() {
        return tabName;
    }

    public String getTabId() {
        return tabId;
    }
}
