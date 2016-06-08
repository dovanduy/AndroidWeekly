package io.github.cyning.droidcore.ui;

import android.view.KeyEvent;
import android.view.View;

/**
 * Author: cyning
 * Date  : 2015.06.08
 * Time  : 下午4:16
 * Desc  : Activity的基类
 */
public class BaseDialogActivity extends BaseActivity {


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeGuide(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void closeGuide(View view) {
        finish();
    }

}
