package io.github.cyning.mobilenews.cartoon.model;

/**
 * @author Cyning
 * @since 2016.04.10
 * Time    12:11 AM
 * Desc    <p>类/接口描述</p>
 */

public class CartoonCommicRecmand {

    /**
     * comic_id : 520942
     * title : 少年术师端木洪
     * cover_url : http://ugc.qpic.cn/manhua_cover/0/27_00_25_97d1a2b98ba70f1fe90a57e0ac9a169d.jpg/0
     */

    private String comic_id;
    private String title;
    private String cover_url;

    public String getComic_id() {
        return comic_id;
    }


    public String getTitle() {
        return title;
    }


    public String getCover_url() {
        return cover_url;
    }

    @Override
    public String toString() {
        return "CartoonCommicRecmand{" +
                "comic_id='" + comic_id + '\'' +
                ", title='" + title + '\'' +
                ", cover_url='" + cover_url + '\'' +
                '}';
    }
}
