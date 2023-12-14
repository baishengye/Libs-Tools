package com.xiaoyingbo.lib_util.BSY.util;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.xiaoyingbo.lib_util.thirdparty.BarUtils;

public class BSYBarUtils {
    ///////////////////////////////////////////////////////////////////////////
    // status bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the status bar's height.
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        return BarUtils.getStatusBarHeight();
    }

    /**
     * Set the status bar's visibility.
     *
     * @param activity  The activity.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final AppCompatActivity activity,
                                              final boolean isVisible) {
        BarUtils.setStatusBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * Set the status bar's visibility.
     *
     * @param window    The window.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Window window,
                                              final boolean isVisible) {
        BarUtils.setStatusBarVisibility(window, isVisible);
    }

    /**
     * Return whether the status bar is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final AppCompatActivity activity) {
        return BarUtils.isStatusBarVisible(activity);
    }

    /**
     * Set the status bar's light mode.
     *
     * @param activity    The activity.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final AppCompatActivity activity,
                                             final boolean isLightMode) {
        BarUtils.setStatusBarLightMode(activity.getWindow(), isLightMode);
    }

    /**
     * Set the status bar's light mode.
     *
     * @param window      The window.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Window window,
                                             final boolean isLightMode) {
        BarUtils.setStatusBarLightMode(window, isLightMode);
    }

    /**
     * Is the status bar light mode.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final AppCompatActivity activity) {
        return BarUtils.isStatusBarLightMode(activity.getWindow());
    }

    /**
     * Is the status bar light mode.
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final Window window) {
        return BarUtils.isStatusBarLightMode(window);
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        BarUtils.addMarginTopEqualStatusBarHeight(view);
    }

    /**
     * Subtract the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        BarUtils.subtractMarginTopEqualStatusBarHeight(view);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     */
    public static View setStatusBarColor(@NonNull final AppCompatActivity activity,
                                         @ColorInt final int color) {
        return BarUtils.setStatusBarColor(activity, color, false);
    }

    /**
     * Set the status bar's color.
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static View setStatusBarColor(@NonNull final AppCompatActivity activity,
                                         @ColorInt final int color,
                                         final boolean isDecor) {
        return BarUtils.setStatusBarColor(activity, color, isDecor);
    }

    /**
     * Set the status bar's color.
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color) {
        BarUtils.setStatusBarColor(fakeStatusBar, color);
    }

    /**
     * Set the custom status bar.
     *
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarCustom(@NonNull final View fakeStatusBar) {
        BarUtils.setStatusBarCustom(fakeStatusBar);
    }

    /**
     * Set the status bar's color for DrawerLayout.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor4Drawer(@NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color) {
        BarUtils.setStatusBarColor4Drawer(drawer, fakeStatusBar, color, false);
    }

    /**
     * Set the status bar's color for DrawerLayout.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                final boolean isTop) {
        BarUtils.setStatusBarColor4Drawer(drawer, fakeStatusBar, color, isTop);
    }

    ///////////////////////////////////////////////////////////////////////////
    // action bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the action bar's height.
     *
     * @return the action bar's height
     */
    public static int getActionBarHeight() {
        return BarUtils.getActionBarHeight();
    }

    ///////////////////////////////////////////////////////////////////////////
    // notification bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Set the notification bar's visibility.
     * <p>Must hold {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(final boolean isVisible) {
        BarUtils.setNotificationBarVisibility(isVisible);
    }

    ///////////////////////////////////////////////////////////////////////////
    // navigation bar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the navigation bar's height.
     *
     * @return the navigation bar's height
     */
    public static int getNavBarHeight() {
        return BarUtils.getNavBarHeight();
    }

    /**
     * Set the navigation bar's visibility.
     *
     * @param activity  The activity.
     * @param isVisible True to set navigation bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final AppCompatActivity activity, boolean isVisible) {
        BarUtils.setNavBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * Set the navigation bar's visibility.
     *
     * @param window    The window.
     * @param isVisible True to set navigation bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Window window, boolean isVisible) {
        BarUtils.setNavBarVisibility(window, isVisible);
    }

    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final AppCompatActivity activity) {
        return BarUtils.isNavBarVisible(activity.getWindow());
    }

    /**
     * Return whether the navigation bar visible.
     * <p>Call it in onWindowFocusChanged will get right result.</p>
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Window window) {
        return BarUtils.isNavBarVisible(window);
    }

    /**
     * Set the navigation bar's color.
     *
     * @param activity The activity.
     * @param color    The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final AppCompatActivity activity, @ColorInt final int color) {
        BarUtils.setNavBarColor(activity.getWindow(), color);
    }

    /**
     * Set the navigation bar's color.
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Window window, @ColorInt final int color) {
        BarUtils.setNavBarColor(window, color);
    }

    /**
     * Return the color of navigation bar.
     *
     * @param activity The activity.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final AppCompatActivity activity) {
        return BarUtils.getNavBarColor(activity);
    }

    /**
     * Return the color of navigation bar.
     *
     * @param window The window.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Window window) {
        return BarUtils.getNavBarColor(window);
    }

    /**
     * Return whether the navigation bar visible.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSupportNavBar() {
        return BarUtils.isSupportNavBar();
    }
}
