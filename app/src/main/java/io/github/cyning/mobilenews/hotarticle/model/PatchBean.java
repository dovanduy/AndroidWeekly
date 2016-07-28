package io.github.cyning.mobilenews.hotarticle.model;

/**
 * @author Cyning
 * @since 2016.07.22
 * Time    下午4:17
 * Desc    <p>类/接口描述</p>
 */

public class PatchBean {
    private String patchId;

    private String patchVersion;

    private String patchurl;

    public String getPatchId() {
        return patchId;
    }

    public PatchBean setPatchId(String patchId) {
        this.patchId = patchId;
        return this;
    }

    public String getPatchVersion() {
        return patchVersion;
    }

    public PatchBean setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
        return this;
    }

    public String getPatchurl() {
        return patchurl;
    }

    public PatchBean setPatchurl(String patchurl) {
        this.patchurl = patchurl;
        return this;
    }

    @Override
    public String toString() {
        return "PatchBean{" +
                "patchId='" + patchId + '\'' +
                ", patchVersion='" + patchVersion + '\'' +
                ", patchurl='" + patchurl + '\'' +
                '}';
    }
}
