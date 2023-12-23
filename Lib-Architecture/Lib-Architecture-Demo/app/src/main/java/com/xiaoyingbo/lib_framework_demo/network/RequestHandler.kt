package com.xiaoyingbo.lib_framework_demo.network

import android.R
import android.R.attr
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.JsonSyntaxException
import com.hjq.gson.factory.GsonFactory
import com.hjq.http.EasyLog
import com.hjq.http.config.IRequestHandler
import com.hjq.http.exception.DataException
import com.hjq.http.exception.NullBodyException
import com.hjq.http.exception.ResponseException
import com.hjq.http.request.HttpRequest
import okhttp3.Headers
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type


class RequestHandler() : IRequestHandler {
    @Throws(Throwable::class)
    override fun requestSuccess(httpRequest: HttpRequest<*>, response: Response, type: Type): Any {
        if (Response::class.java == type) {
            return response
        }

        if (!response.isSuccessful) {
            throw ResponseException(
                java.lang.String.format(
                    "",
                    response.code(), response.message()
                ), response
            )
        }

        if (Headers::class.java == type) {
            return response.headers()
        }

        val body = response.body()
            ?: throw NullBodyException("")

        if (ResponseBody::class.java == type) {
            return body
        }
        // 如果是用数组接收，判断一下是不是用 byte[] 类型进行接收的
        if (type is GenericArrayType) {
            val genericComponentType = (type as GenericArrayType).genericComponentType
            if (Byte::class.javaPrimitiveType == genericComponentType) {
                return body.bytes()
            }
        }

        if (InputStream::class.java == type) {
            return body.byteStream()
        }

        if (Bitmap::class.java == type) {
            return BitmapFactory.decodeStream(body.byteStream())
        }

        val text: String
        text = try {
            body.string()
        } catch (e: IOException) {
            // 返回结果读取异常
            throw DataException("", e)
        }

        // 打印这个 Json 或者文本
        EasyLog.printJson(httpRequest, text)

        if (String::class.java == type) {
            return text
        }

        val result: Any

        try {
            result = GsonFactory.getSingletonGson().fromJson(text, type)
        } catch (e: JsonSyntaxException) {
            // 返回结果读取异常
            throw DataException("", e)
        }

        return result
    }

    override fun requestFail(httpRequest: HttpRequest<*>, throwable: Throwable): Throwable {
       return Exception()
    }

    override fun downloadFail(httpRequest: HttpRequest<*>, throwable: Throwable): Throwable {
        return requestFail(httpRequest, throwable)
    }

    override fun readCache(httpRequest: HttpRequest<*>, type: Type, cacheTime: Long): Any {
       return ""
    }

    override fun writeCache(httpRequest: HttpRequest<*>, response: Response, result: Any): Boolean {

        return false
    }

    override fun clearCache() {

    }
}