package com.xiaoyingbo.lib_widget.normal.view.titleBar;

/**
 *    desc   : 标题栏点击监听接口
 */
public interface OnBSYTitleBarListener {

    /**
     * 左项被点击
     * @param BSYTitleBar
     */
    default void onLeftClick(BSYTitleBar BSYTitleBar) {}

    /**
     * 标题被点击
     * @param BSYTitleBar
     */
    default void onTitleClick(BSYTitleBar BSYTitleBar) {}

    /**
     * 右项被点击
     * @param BSYTitleBar
     */
    default void onRightClick(BSYTitleBar BSYTitleBar) {}
}