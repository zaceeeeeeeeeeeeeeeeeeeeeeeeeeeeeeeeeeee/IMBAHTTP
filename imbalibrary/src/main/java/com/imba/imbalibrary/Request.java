package com.imba.imbalibrary;

import java.util.Map;

/**
 * Created by zace on 2015/4/26.
 */
public class Request {

    private String url;
    private Map<String, String> header;
    private String content;
    private int maxRetryCount = 5;
    private ICallBack callBack;
    private OnGlobleExceptionListener listener;

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public OnGlobleExceptionListener getListener() {
        return listener;
    }

    public void setOnGlobleExceptionListener(OnGlobleExceptionListener listener) {
        this.listener = listener;
    }

    public enum RequestMethod {GET, POST, DELETE, PUT}

    private RequestMethod method;

    public ICallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Request(String url) {
        this.url = url;
        this.method = RequestMethod.GET;
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
