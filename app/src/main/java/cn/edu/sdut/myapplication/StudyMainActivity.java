package cn.edu.sdut.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class StudyMainActivity extends AppCompatActivity {
    Chronometer jishiqi;
    Button btnSendSms;
    int shijian=10;
    Spinner spinnerCourse;
    Button btnListView;
//    ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,array);
    Long t1,t2;
    Button btnSingle;
    Button btnMulti;
    Button btnCustom,btnTimeDialog,btnDateDialog,btnDatetimeDialog;
    Integer singleChecked;
    String[] course;
    //模拟获取远程数据
    protected String[] getData(){
        String[] data=new String[]{"aaaa","bbbb","cccc"};
        return data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        t1=System.currentTimeMillis();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stuty_main_layout);//R.类.名称
        btnSendSms =findViewById(R.id.btnDome);
        btnListView=findViewById(R.id.btn_list_view);
        jishiqi=findViewById(R.id.jishiqi);
        spinnerCourse=findViewById(R.id.spinner_course);
         btnSingle=findViewById(R.id.btn_single);
         btnMulti=findViewById(R.id.btn_multi);
         btnCustom=findViewById(R.id.btn_custom);
         singleChecked=0;
         btnDateDialog=findViewById(R.id.btn_date_dialog);
         btnTimeDialog=findViewById(R.id.btn_time_dialog);
         btnDatetimeDialog=findViewById(R.id.btn_datetime_dialog);
        course=getResources().getStringArray(R.array.course);

        btnDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(StudyMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(StudyMainActivity.this, year+" "+month+" "+dayOfMonth, Toast.LENGTH_SHORT).show();
                    }
                }, 2023, 5, 14);
                datePickerDialog.show();

            }
        });
        btnTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(StudyMainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(StudyMainActivity.this, hourOfDay+" "+ minute, Toast.LENGTH_SHORT).show();
                    }
                }, 15, 20, true);
                timePickerDialog.show();
            }
        });
        //单选按钮
         btnSingle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog dialog;
                 AlertDialog.Builder builder=new AlertDialog.Builder(StudyMainActivity.this);
                 dialog=builder
                         .setTitle("单选列表 ")
                         .setSingleChoiceItems(R.array.course, singleChecked, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int selected) {
                                 singleChecked=selected;
                             }
                         })
                         .setIcon(R.mipmap.ic_launcher)
                         .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 // 用户点击确定后的动作
                                 Toast.makeText(StudyMainActivity.this, which, Toast.LENGTH_SHORT).show();
                             }
                         })
//                         .setMessage("注册成功")
                         .create();
                 dialog.show();
             }
         });

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(StudyMainActivity.this);
                View view1= LayoutInflater.from(StudyMainActivity.this).inflate(R.layout.dialog_layout,null);
                EditText dialog_xm=view1.findViewById(R.id.dialog_xm);
                EditText dailog_mm=view1.findViewById(R.id.dialog_mm);
                dialog=builder
                        .setTitle("自定义对话框 ")
                        .setView(view1)//做交互一定用view
                        .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(StudyMainActivity.this, dialog_xm.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
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
                Toast.makeText(StudyMainActivity.this, spinnerCourse.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //btnListView按钮动作
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump activity_seasons.xml
                Intent intent=new Intent(StudyMainActivity.this,ListViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            t2=System.currentTimeMillis();
            if(t2-t1<2000){
                StudyMainActivity.this.finish();
            }else{
                t1=t2;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();

            }
        }
        return true;
    }
}