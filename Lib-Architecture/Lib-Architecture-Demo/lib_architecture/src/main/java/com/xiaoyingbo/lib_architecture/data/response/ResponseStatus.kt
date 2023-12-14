/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaoyingbo.lib_architecture.data.response;

import androidx.annotation.NonNull;

/**
 *本类仅用作示例参考，请根据 "实际项目需求" 配置自定义的 "响应状态元信息"
 * <p>
 * Create by KunMinX at 19/10/11
 */
public class ResponseStatus {

    private String responseMsg = "";
    private boolean success = true;
    private @ResultSource int source = ResultSource.NETWORK;

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public ResponseStatus() {
    }

    public ResponseStatus(String responseMsg, boolean success) {
        this(responseMsg,success,"");
    }

    public ResponseStatus(boolean success, @ResultSource int source) {
        this("",success,"",source);
    }

    public ResponseStatus(String responseMsg, boolean success, @NonNull String defaultResponseMsg) {
        this(responseMsg,success,defaultResponseMsg,ResultSource.NETWORK);
    }

    public ResponseStatus(String responseMsg, boolean success, @ResultSource int source) {
        this(responseMsg,success,"",source);
    }

    public ResponseStatus(String responseMsg, boolean success, @NonNull String defaultResponseMsg,@ResultSource int source) {
        this.responseMsg = responseMsg;
        this.success = success;
        if(success&&responseMsg==null){
            this.responseMsg= defaultResponseMsg;
        }
        this.source = source;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    @ResultSource
    public int getSource() {
        return source;
    }
}
