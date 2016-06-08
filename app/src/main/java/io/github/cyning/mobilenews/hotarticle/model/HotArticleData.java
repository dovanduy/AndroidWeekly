package io.github.cyning.mobilenews.hotarticle.model;

import io.github.cyning.greendao.HotArticle;
import java.util.List;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    1:19 PM
 * Desc    <p>类/接口描述</p>
 */

public class HotArticleData {


    private int error;


    private List<HotArticle> results;

    public List<HotArticle> getResults() {
        return results;
    }

}
