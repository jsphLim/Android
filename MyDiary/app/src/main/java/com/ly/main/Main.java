package com.ly.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.ly.Login.R;
/**
 * Created by sd on 2017/11/10.
 */

public class Main extends AppCompatActivity {
    private  BottomNavigationBar bottom_navigation_bar;
    private  TextBadgeItem badge;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        bottom_navigation_bar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        initBottomNavigationBar();
    }

    public void initBottomNavigationBar() {
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        //设置默认颜色
        bottom_navigation_bar
                .setInActiveColor(R.color.colorInActive)//设置未选中的Item的颜色，包括图片和文字
                .setActiveColor(R.color.bg_toolbar)
                .setBarBackgroundColor(R.color.colorBarBg);//设置整个控件的背景色
        //设置徽章
        badge=new TextBadgeItem()
//                .setBorderWidth(2)//Badge的Border(边界)宽度
//                .setBorderColor("#FF0000")//Badge的Border颜色
//                .setBackgroundColor("#9ACD32")//Badge背景颜色
//                .setGravity(Gravity.RIGHT| Gravity.TOP)//位置，默认右上角
                .setText("2")//显示的文本
//                .setTextColor("#F0F8FF")//文本颜色
//                .setAnimationDuration(2000)
//                .setHideOnSelect(true)//当选中状态时消失，非选中状态显示
        ;
        //添加选项
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.chat_icon,"日记"))
                .addItem(new BottomNavigationItem(R.mipmap.chat_icon, "聊天"))
                .addItem(new BottomNavigationItem(R.mipmap.ftp_icon, "文件"))
                .initialise();//所有的设置需在调用该方法前完成

        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });

    }
}
