package com.imba.imbalibrary;

import android.os.AsyncTask;

import com.imba.exception.AppException;

import java.net.HttpURLConnection;

/**
 * Created by zace on 2015/4/26.
 */
public class RequestTask extends AsyncTask<Void, Integer, Object> {

    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... params) {

        Object o = request.getCallBack().preRequest();
        if (o != null) {
            return o;
        }
        return request(0);
    }

    private Object request(int retry) {
        try {

            if (request.checkIsCanceled()) {
                throw new AppException(AppException.ErrorType.CANCEL, "request has been canceled");
            }

            HttpURLConnection connection = ImbaHttp.excute(request);
            return request.getCallBack().parsr(connection, new OnProgressUpdateListener() {
                @Override
                public void onProgressUpdaet(int curLen, int totalLen) {
                    publishProgress(curLen, totalLen);
                }
            });
        } catch (AppException e) {
            if (e.getType() == AppException.ErrorType.TIMEOUT) {
                if (retry < request.getMaxRetryCount()) {
                    retry++;
                    return request(retry);
                }
            }
            return e;
        }
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof AppException) {
            if (request.getListener() != null) {
                request.getListener().handleException((AppException) o);
            } else {
                request.getCallBack().onFailure((AppException) o);
            }
        } else {
            request.getCallBack().onSuccess(o);
        }
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        request.getCallBack().onProgressUpdate(values[0], values[1]);

    }

}
