package io.github.cyning.mobilenews.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Cyning
 * @since 2016.03.25
 * Time    11:49 PM
 * Desc    <p>tab frament</p>
 */

public class TabFragment {
    private  String tag;
    private  Class clazz;
    private Bundle argument;

    public TabFragment(String tag, Class clazz, Bundle argument) {
        this.tag = tag;
        this.clazz = clazz;
        this.argument = argument;
    }

    public TabFragment(String tag, Class clazz) {
        this.tag = tag;
        this.clazz = clazz;
    }

    public String getTag() {
        return tag;
    }

    public TabFragment setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Class<Fragment> getClazz() {
        return clazz;
    }

    public TabFragment setClazz(Class<Fragment> clazz) {
        this.clazz = clazz;
        return this;
    }

    public Bundle getArgument() {
        return argument;
    }

    public TabFragment setArgument(Bundle argument) {
        this.argument = argument;
        return this;
    }
}
