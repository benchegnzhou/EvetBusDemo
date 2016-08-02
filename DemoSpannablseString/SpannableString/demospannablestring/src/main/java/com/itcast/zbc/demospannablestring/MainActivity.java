package com.itcast.zbc.demospannablestring;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.text4)
    TextView text4;
    @Bind(R.id.text5)
    TextView text5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //1.让文本和图片一起显示
        text1.setText(showTextWithImage("我是文本[大兵]", R.mipmap.ic_launcher));

        //2.让某段文字变色
        text2.setText(showTextWithColor("王二,小明,大兵等3人觉得很赞", Color.RED));

        //3.让某段文字可以被点击并跳转超链接(适用于文本中包含html代码的)
        String text = "详情请点击<a href='http://www.baidu.com'>百度</a>";  //html标签自动解析
        Spanned spanned = Html.fromHtml(text);
        text3.setText(spanned);
        text3.setMovementMethod(LinkMovementMethod.getInstance());//设置可以点击超链接

        //4.让某段文字可以被点击并自定义点击的逻辑操作
        String string2 = "王二,小明,大兵等3人觉得很赞";
        SpannableString ss = new SpannableString(string2);
        MyUrlSpan myUrlSpan = new MyUrlSpan(string2.substring(0, string2.indexOf(",")));
        ss.setSpan(myUrlSpan, 0, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        text4.setText(ss);
        text4.setMovementMethod(LinkMovementMethod.getInstance());  //设置可以点击超链接

        //可以让多个SpaanableString拼接在一起
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(spanned);
        builder.append(ss);
        text5.setText(builder);
		 text5.setMovementMethod(LinkMovementMethod.getInstance());  //设置可以点击超链接

    }


    /**
     * 让某几个文字显示颜色
     *
     * @param text
     * @param color
     * @return
     */
    private CharSequence showTextWithColor(String text, int color) {


        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(color);  //前景色
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(color);
        BackgroundColorSpan colorSpan3 = new BackgroundColorSpan(Color.GREEN);  //文字的背景色


        //BackgroundColorSpan    背景色
        int end = text.indexOf("等");

        //SpannableString.SPAN_INCLUSIVE_EXCLUSIVE 是一种简单的稳步格式的插入方式： 一句话说就是：包括前面不包括后面
        //注意： setSpan只能对应一个唯一的colorSpan1对象   //span ； 块级元素
        ss.setSpan(colorSpan1, 0, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(colorSpan2, end + 1, end + 3, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(colorSpan3, text.length() - 1, text.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 让图片和文字一起显示
     *
     * @param text
     * @param imageRes
     * @return
     */
    private SpannableString showTextWithImage(String text, int imageRes) {
        //1.获取富文本文字
        SpannableString ss = new SpannableString(text);
        //2.替换的素材
        Drawable drawable = getResources().getDrawable(imageRes);


        //必须设置drawable的边界
//		drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());   //保持图pain的宽高
        drawable.setBounds(0, 0, 40, 40);
        ImageSpan span = new ImageSpan(drawable);//能够放在字符串中的图片了
        ImageSpan span2 = new ImageSpan(drawable);//能够放在字符串中的图片了

        //表情标识
//		/smile  /cry    家即时通讯中，表情和文字一起发送试用的是这种方式，用特殊的符号表示表情，客户端解码
//		[smile] [cry]

        //我是文本[大兵]
        int start = text.indexOf("[");
        int end = text.indexOf("]") + 1;

        //使用的时候注意：一个setSpan不许对应一个新的span，不可以多次使用
        ss.setSpan(span, 1, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(span2, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }

    class MyUrlSpan extends URLSpan {
        public MyUrlSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            //默认实现是获取url，打开浏览器，但是我需要自定义点击的逻辑操作
            Toast.makeText(MainActivity.this, getURL(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.GREEN);//设置文字的颜色
            ds.setUnderlineText(false);//设置是否显示下划线
        }
    }
}

