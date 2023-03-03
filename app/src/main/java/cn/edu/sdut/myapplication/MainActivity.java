package cn.edu.sdut.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);//R.类.名称
        //R是Android内部的类，自动的掌管Android的资源
        // （.的实质是static类）
        //.名称  是系统会自动增加一个int类型的常量
        //因此图片文件名，必须符合名称规则，不然无法映射成int常量。且主文件名不能重复
    }
}