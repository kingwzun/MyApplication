package cn.edu.sdut.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Chronometer jishiqi;
    Button btnSendSms;
    int shijian=10;
    Spinner spinnerCourse;
    Long t1,t2;
    //模拟获取远程数据
    protected String[] getData(){
        String[] data=new String[]{"aaaa","bbbb","cccc"};
        return data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        t1=System.currentTimeMillis();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);//R.类.名称
        btnSendSms =findViewById(R.id.btnDome);
        jishiqi=findViewById(R.id.jishiqi);
        spinnerCourse=findViewById(R.id.spinner_course);
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

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,getData());
        spinnerCourse.setAdapter(arrayAdapter);
        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//position表示选择的是第几项，id一般不用
                Toast.makeText(MainActivity.this, spinnerCourse.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            t2=System.currentTimeMillis();
            if(t2-t1<2000){
                MainActivity.this.finish();
            }else{
                t1=t2;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();

            }
        }
        return true;
    }
}