package com.android.myres.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import timber.log.Timber;

/**
 * @author Perry Lance
 * @since 2024-05-16 Created
 */
public class KeyboardUtils {

    private static int sDecorViewDelta = 0;

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isSoftInputVisible(Activity activity) {
        return getDecorViewInvisibleHeight(activity.getWindow()) > 0;
    }

    private static int getDecorViewInvisibleHeight(Window window) {
        View decorView = window.getDecorView();
        Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    private static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId != 0 ? res.getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * Don't set adjustResize
     */
    public static void fixAndroidBug5497(Activity activity) {
        final Window window = activity.getWindow();
        final FrameLayout contentView = window.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        final int[] contentViewInvisibleHeightPre5497 = {getContentViewInvisibleHeight(window)};
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = getContentViewInvisibleHeight(window);
                if (contentViewInvisibleHeightPre5497[0] != height) {
                    contentViewChild.setPadding(
                            contentViewChild.getPaddingLeft(),
                            contentViewChild.getPaddingTop(),
                            contentViewChild.getPaddingRight(),
                            paddingBottom + getDecorViewInvisibleHeight(window)
                    );
                    contentViewInvisibleHeightPre5497[0] = height;
                }
            }
        });
    }

    private static int getContentViewInvisibleHeight(Window window) {
        View contentView = window.findViewById(android.R.id.content);
        if (contentView == null) return 0;
        Rect outRect = new Rect();
        contentView.getWindowVisibleDisplayFrame(outRect);
        Timber.d("getContentViewInvisibleHeight: " + (contentView.getBottom() - outRect.bottom));
        int delta = Math.abs(contentView.getBottom() - outRect.bottom);
        return delta <= getStatusBarHeight() + getNavBarHeight() ? 0 : delta;
    }

    private static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
