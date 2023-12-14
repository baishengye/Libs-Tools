package com.xiaoyingbo.lib_architecture.data.response

/**
 * 专用于数据层返回结果至 domain 层或 ViewModel，原因如下：
 *
 *
 * liveData 专用于页面开发、解决生命周期安全问题，
 * 有时数据并非通过 liveData 分发给页面，也可是通过其他方式通知非页面组件，
 * 这时 repo 方法中内定通过 liveData 分发便不合适，不如一开始就规定不在数据层通过 liveData 返回结果
 */
data class DataResult<T>(
    val result: T,
    val responseStatus: ResponseStatus
) {
    constructor(entity: T):this(entity,ResponseStatus())

    interface Result<T> {
        fun onResult(dataResult: DataResult<T>)
    }
}
