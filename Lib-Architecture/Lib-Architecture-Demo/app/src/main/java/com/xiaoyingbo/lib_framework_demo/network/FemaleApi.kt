package com.xiaoyingbo.lib_framework_demo.network

import com.hjq.http.config.IRequestApi

class FemaleApi:IRequestApi {
    override fun getApi(): String {
        return "api/image/girl/list/random?app_id=${RequestServer.APP_ID}&app_secret=${RequestServer.APP_SECRET}"
    }

    data class Female(
        val code: Int,
        val `data`: List<Data>,
        val msg: String
    )

    data class Data(
        val imageFileLength: Int,
        val imageSize: String,
        val imageUrl: String
    )
}