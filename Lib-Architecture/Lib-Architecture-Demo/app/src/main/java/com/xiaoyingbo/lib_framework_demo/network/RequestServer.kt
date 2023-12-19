package com.xiaoyingbo.lib_framework_demo.network

import com.hjq.http.config.IRequestBodyStrategy
import com.hjq.http.config.IRequestServer
import com.hjq.http.model.RequestBodyType


class RequestServer : IRequestServer {
    companion object{
        const val APP_ID = "benqemitujkuopga"
        const val APP_SECRET = "dGZZUVlJUC9mSGpyVDR2ZGl0R3BVdz09"
    }

    override fun getHost(): String {
        return "https://www.mxnzp.com/"
    }

    override fun getBodyType(): IRequestBodyStrategy {
        // 参数以 Json 格式提交（默认是表单）
        return RequestBodyType.JSON
    }
}