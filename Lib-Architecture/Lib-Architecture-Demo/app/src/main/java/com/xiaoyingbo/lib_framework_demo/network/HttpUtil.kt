package com.xiaoyingbo.lib_framework_demo.network

import androidx.lifecycle.LifecycleOwner
import com.hjq.http.EasyConfig
import com.hjq.http.EasyHttp
import com.hjq.http.listener.OnHttpListener
import com.hjq.http.model.ResponseClass
import com.xiaoyingbo.lib_architecture.data.response.DataResult
import com.xiaoyingbo.lib_architecture.data.response.ResponseStatus
import com.xiaoyingbo.lib_architecture.data.response.ResultSource
import okhttp3.OkHttpClient
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.jvm.Throws


class HttpUtil {
    companion object{
        val instance = HttpUtil()
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        EasyConfig.with(okHttpClient) // 是否打印日志
            .setLogEnabled(true) // 设置服务器配置（必须设置）
            .setServer(RequestServer()) // 设置请求处理策略（必须设置）
            .setHandler(RequestHandler()) // 设置请求重试次数
            .setRetryCount(3) // 添加全局请求参数
            .into()
    }

    @Throws(Exception::class)
    suspend fun getFemale(lifecycleOwner: LifecycleOwner):FemaleApi.Female? = suspendCoroutine {con->
        EasyHttp.post(lifecycleOwner)
            .api(FemaleApi())
            .request(object : OnHttpListener<FemaleApi.Female>{
                override fun onHttpSuccess(result: FemaleApi.Female) {
                    DataResult<FemaleApi.Female>(result, ResponseStatus("","成功",true,ResultSource.NETWORK))
                    con.resume(result)
                }

                override fun onHttpFail(throwable: Throwable) {
                    con.resume(null)
                }
            })
    }
}