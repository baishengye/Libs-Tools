package com.xiaoyingbo.lib_architecture.ui.viewModel.mvi

class MVIInterface {
    /** 用户与ui的交互事件*/
    interface IAction//对应Intent
    /** ui响应的状态*/
    interface MState//对应Model,多次使用的数据
    /** ui响应的事件*/
    interface MEffect//对应Model,单次使用的数据
}