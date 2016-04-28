package com.imba.sample;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.imba.imbalibrary.FileCallBack;
import com.imba.imbalibrary.Request;
import com.imba.imbalibrary.RequestTask;
import com.imba.util.PermissionsUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityMainActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doSome(View view) {
        if (!PermissionsUtil.verifyStoragePermissions(this)) {
            PermissionsUtil.requestPermissions(this);
        } else {
            download();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == PermissionsUtil.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void download() {
        Request request = new Request("http://img3.imgtn.bdimg.com/it/u=1845194783,3688015352&fm=21&gp=0.jpg", Request.RequestMethod.POST);
//        String content = "";
//        request.setContent(content);
        request.setMethod(Request.RequestMethod.GET);
        String path = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";

        Log.i(TAG, path);
        request.setCallBack(new FileCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, result);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onProgressUpdate(long currentLen, long totalLen) {
                Log.i(TAG, "currentLen=" + currentLen + "******totalLen=" + totalLen);
            }
        }.setFilePath(path));
        RequestTask task = new RequestTask(request);
        task.execute();
    }
}