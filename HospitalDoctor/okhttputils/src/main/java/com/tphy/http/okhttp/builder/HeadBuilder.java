package com.tphy.http.okhttp.builder;

import com.tphy.http.okhttp.OkHttpUtils;
import com.tphy.http.okhttp.request.RequestCall;
import com.tphy.http.okhttp.request.OtherRequest;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
