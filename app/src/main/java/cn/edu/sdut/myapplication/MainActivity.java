package cn.edu.sdut.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    Chronometer jishiqi;
    Button btnSendSms;
    int shijian=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);//R.类.名称
        btnSendSms =findViewById(R.id.btnDome);
        jishiqi=findViewById(R.id.jishiqi);
        jishiqi.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                shijian--;
                if(shijian>0){
                    btnSendSms.setText(shijian+"秒后重新发送");
                }else{
                    btnSendSms.setEnabled(true);
                    btnSendSms.setText(R.string.btnSendSmsText);
                }
            }
        });
        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shijian=10;
                jishiqi.start();
                btnSendSms.setEnabled(false);
            }
        });
        //R是Android内部的类，自动的掌管Android的资源
        // （.的实质是static类）
        //.名称  是系统会自动增加一个int类型的常量
        //因此图片文件名，必须符合名称规则，不然无法映射成int常量。且主文件名不能重复
    }
}