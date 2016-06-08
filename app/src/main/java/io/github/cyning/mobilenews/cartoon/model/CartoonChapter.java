package io.github.cyning.mobilenews.cartoon.model;

/**
 * @author Cyning
 * @since 2016.04.10
 * Time    12:09 AM
 * Desc    <p>类/接口描述</p>
 */

public class CartoonChapter {

    /**
     * chapter_id : 717
     * seq_no : 708
     * vip_state : 1
     */

    private String chapter_id;
    private String seq_no;
    private String vip_state;

    public String getChapter_id() {
        return chapter_id;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public String getVip_state() {
        return vip_state;
    }

    @Override
    public String toString() {
        return "CartoonChapters{" +
                "chapter_id='" + chapter_id + '\'' +
                ", seq_no='" + seq_no + '\'' +
                ", vip_state='" + vip_state + '\'' +
                '}';
    }
}
