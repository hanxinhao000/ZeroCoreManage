package com.xinhao.zerocoremanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.xinhao.zerocoremanage.utils.UUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVersion = findViewById(R.id.version);
        mVersion.setText("ZeroTermuxEngine\n" + UUtils.getVersionName(this));

    }
}