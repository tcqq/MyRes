package com.android.myres.common.base;

import android.graphics.Rect;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.myres.utils.KeyboardUtils;

import timber.log.Timber;

/**
 * @author Perry Lance
 * @since 2024-05-16 Created
 */
public abstract class BaseActivity extends AppCompatActivity {

    private float actionDownX = 0f;
    private float actionDownY = 0f;

    protected boolean enableBlankTouch() {
        return true;
    }

    public void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    public void setActionBarTitle(@StringRes int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }

    public void setActionBarTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setActionBarSubtitle(@StringRes int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(resId);
        }
    }

    public void setActionBarSubtitle(CharSequence subtitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Click on the blank space to close the soft keyboard and make the EditText lose focus.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (enableBlankTouch()) {
            float x = event.getX();
            float y = event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                actionDownX = x;
                actionDownY = y;
            } else if (event.getAction() == MotionEvent.ACTION_UP && !(Math.abs(x - actionDownX) > 50 || Math.abs(y - actionDownY) > 50)) {
                View v = getCurrentFocus();
                if (v instanceof EditText) {
                    Rect outRect = new Rect();
                    v.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        Timber.v("Hide keyboard");
                        KeyboardUtils.hideKeyboard(this);
                        v.setFocusable(false);
                        v.setFocusableInTouchMode(true);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
