package com.xiaoyingbo.lib_architecture.data.response

import androidx.annotation.IntDef
import com.xiaoyingbo.lib_architecture.data.response.ResultSource

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    ResultSource.NETWORK,
    ResultSource.DATABASE,
    ResultSource.LOCAL_FILE,
    ResultSource.SHARE_PREFERENCE,
    ResultSource.MMKV,
    ResultSource.BLUETOOTH,
    ResultSource.OTHER
)
annotation class ResultSource {
    companion object {
        const val NETWORK = 0x1
        const val DATABASE = 0x2
        const val LOCAL_FILE = 0x3
        const val MMKV = 0x4
        const val SHARE_PREFERENCE = 0x5
        const val BLUETOOTH = 0x06
        const val OTHER = 0x07
    }
}
