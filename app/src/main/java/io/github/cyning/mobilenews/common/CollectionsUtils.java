package io.github.cyning.mobilenews.common;

import java.util.List;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    10:42 PM
 * Desc    <p>类/接口描述</p>
 */

public class CollectionsUtils {
    public static boolean hasEle(List images) {
        if (images == null || images.size() == 0){
            return false;
        }else {
            return true;
        }
    }
}
