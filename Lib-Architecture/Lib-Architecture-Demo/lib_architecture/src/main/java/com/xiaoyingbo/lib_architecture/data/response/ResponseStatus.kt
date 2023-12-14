package com.xiaoyingbo.lib_architecture.data.response

/**
 * 本类仅用作示例参考，请根据 "实际项目需求" 配置自定义的 "响应状态元信息"
 *
 *
 * Create by KunMinX at 19/10/11
 */
data class ResponseStatus(
    var responseMsg: String? = "",
    var isSuccess:Boolean = true,
    @ResultSource var source:Int = ResultSource.NETWORK
) {
    constructor(): this("", true, ResultSource.NETWORK)
    constructor(success: Boolean, @ResultSource source: Int) : this("", success, source)

    @JvmOverloads
    constructor(
        responseMsg: String?,
        success: Boolean,
        defaultResponseMsg: String = "",
        @ResultSource source: Int = ResultSource.NETWORK
    ):this(responseMsg,success,source) {
        if (success && responseMsg == null) {
            this.responseMsg = defaultResponseMsg
        }
    }
}
