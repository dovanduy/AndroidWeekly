package io.github.cyning.mobilenews.cartoon.model;

/**
 * @author Cyning
 * @since 2016.04.10
 * Time    12:12 AM
 * Desc    <p>类/接口描述</p>
 */

public class CartoonComicWrapper {

    private int error_code;
    private DataWrapper data;


    class DataWrapper{
        private CartoonComic comic;
        private CartoonChapter chapters;
        private CartoonCommicRecmand recommend;
//        private Object cartoon;


        public CartoonComic getComic() {
            return comic;
        }

        public CartoonChapter getChapters() {
            return chapters;
        }

        public CartoonCommicRecmand getRecommend() {
            return recommend;
        }

        @Override
        public String toString() {
            return "DataWrapper{" +
                    "comic=" + comic +
                    ", chapters=" + chapters +
                    ", recommend=" + recommend +
                    '}';
        }
    }

}

