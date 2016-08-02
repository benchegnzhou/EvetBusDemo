package com.itcast.zbc.eventbus;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 当击btn_try按钮的时候，跳到第二个Activity，
 * 当点击第二个activity上面的First Event按钮的时候向第一个Activity发送消息，
 * 当第一个Activity收到消息后，一方面将消息Toast显示，一方面放入textView中显示。
 */
public class MainActivity extends AppCompatActivity {

    private TextView tv;
    ThreadMode threadMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        Button btn = (Button) findViewById(R.id.btn_try);
        Button btn2 = (Button) findViewById(R.id.btn_delay_msg);
        Button btn3 = (Button) findViewById(R.id.btn_second_msg);
        tv = (TextView) findViewById(R.id.tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this,
                        SecondActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemClock.sleep(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new SomethingElse("子线程的消息"));
                    }
                }).start();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemClock.sleep(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new MessageEvent("第二种事件类型的事件！"));

                    }
                }).start();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //注册一下
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销一下
        EventBus.getDefault().unregister(this);

    }

    private String TAG = MainActivity.class.getSimpleName();

    //    @Subscribe
//    public void onEventMainThread(MessageEvent event) {
//
//        String msg = "onEventMainThread收到了消息：" + event.getMsg();
//        Log.e(TAG, "onEventMainThread: "+msg );
//        tv.setText(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//    }
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.e(TAG, "onEventMainThread: " + msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onMessageEvent(SecondMessage event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.e(TAG, "onEventMainThread: " + msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    public void handleSomethingElse(SomethingElse evElse) {
        String msg = "onEventMainThread收到了消息：" + evElse.getMsg();
        Log.e(TAG, "onEventMainThread: " + msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

}
