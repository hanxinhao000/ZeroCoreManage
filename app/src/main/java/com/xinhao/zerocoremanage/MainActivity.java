package com.xinhao.zerocoremanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinhao.zerocoremanage.utils.UUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mVersion;
    private ImageView mGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVersion = findViewById(R.id.version);
        mGithub = findViewById(R.id.github);
        mVersion.setText("ZeroTermuxEngine\n" + UUtils.getVersionName(this));
        mGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://github.com/hanxinhao000/ZeroCoreManage"));//Url 就是你要打开的网址
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
            }
        });


    }
}