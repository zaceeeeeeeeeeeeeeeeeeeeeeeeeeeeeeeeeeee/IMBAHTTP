package com.imba.imbalibrary;

import android.webkit.URLUtil;

import com.imba.exception.AppException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by zace on 2015/4/26.
 */
public class ImbaHttp {

    public static HttpURLConnection excute(Request request) throws AppException {

        if (!URLUtil.isNetworkUrl(request.getUrl())) {
            throw new AppException("url:" + request.getUrl() + "is not valid");
        }

        switch (request.getMethod()) {
            case GET:
            case DELETE:
                return get(request);
            case PUT:
            case POST:
                return post(request);
        }
        return null;
    }


    private static HttpURLConnection get(Request request) throws AppException {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(15 * 1000);
            addHeader(conn, request.getHeader());
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }

        return conn;
    }


    private static HttpURLConnection post(Request request) throws AppException {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(15 * 1000);

            addHeader(conn, request.getHeader());

            OutputStream os = conn.getOutputStream();
            os.write(request.getContent().getBytes());
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }

        return conn;

    }

    private static void addHeader(HttpURLConnection conn, Map<String, String> header) {

        if (header == null || header.size() == 0)
            return;

        for (Map.Entry<String, String> entry : header.entrySet()) {
            conn.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }


}
