package com.itcast.zbc.eventbus;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zbc on 2016/8/2.
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void click(View v){
        EventBus.getDefault().post(new MessageEvent("点击事件执行了！"));
        finish();
        }
    public void miss(View v){
        EventBus.getDefault().post(new MessageEvent("界面而的事件！"));
        finish();
    }





}
