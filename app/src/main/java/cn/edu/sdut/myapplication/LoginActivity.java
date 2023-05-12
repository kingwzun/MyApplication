package cn.edu.sdut.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    Button btnView,btnSeasons,btnMyHome,btnMyRegister,btnLogin;
    EditText edit_xm,edit_mm;
    RadioButton rb_js,rb_xs;
    ToggleButton toggle;
    RadioGroup radioGroup;
    CheckBox  checkbox;
    Handler handler;
    ProgressDialog progressDialog;
    String xm,mm;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
//    SharedPreferences
    private void setFindViewById(){
       btnLogin=findViewById(R.id.btn_my_login);
       btnView=findViewById(R.id.btnViewDome);
       btnSeasons=findViewById(R.id.btnSeasonDome);
       btnMyHome=findViewById(R.id.btn_my_home);
       btnMyRegister=findViewById(R.id.btn_my_register);

       edit_xm=findViewById(R.id.edit_login);
       edit_mm = findViewById(R.id.edit_register);
       toggle= findViewById(R.id.TgBtm);
       rb_js= findViewById(R.id.rb_js);
       rb_xs= findViewById(R.id.rb_xs);
       radioGroup=findViewById(R.id.usertype);
       checkbox=findViewById(R.id.checkbox);
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_login);
        fullScreenTool();//full screen
        setFindViewById();


        sp=getSharedPreferences("abcd",MODE_PRIVATE);
        editor=sp.edit();

        edit_xm.setText(sp.getString("xm",""));
        edit_mm.setText(sp.getString("mm",""));
        checkbox.setChecked(sp.getBoolean("check",false)); // 恢复时也把记住密码的勾选上

        //进度条
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在验证中");
        progressDialog.setCancelable(false);//设置不允许 用户点击返回键取消

        //Handler是一套Android 消息传递机制,主要用于线程间通信
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                progressDialog.dismiss();//close progressDialog
                int result=msg.what;
                switch (result){
                    case 1:

                        Intent intent=new Intent(LoginActivity.this, StudyMainActivity.class);
//                        intent.putExtra("xm",xm);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };





       btnLogin.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
           @NonNull
           @Override
           public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
               return null;
           }
       });

        btnMyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                dialog=builder
                        .setTitle("注册成功 ")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("注册成功")
                        .create();
                dialog.show();

            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(LoginActivity.this,"记住用户名", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, WebViewActivity.class);
                startActivity(intent);

            }
        });


        //登录按钮动作
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xm=edit_xm.getText().toString();
                mm=edit_mm.getText().toString();
                if(checkbox.isChecked()){
                    editor.putString("xm",xm);
                    editor.putString("mm",mm);

                }else{
                    editor.putString("xm","");
                    editor.putString("mm","");
                }
                editor.putBoolean("check",checkbox.isChecked());
                editor.commit();//must commit

                progressDialog.show();
                //goto login_sub_thread
                new LoginThread().start();
                //关闭登录界面（点击返回按键不会再回到登陆界面）
                //LoginActivity.this.finish();
            }
        });


        //四季按钮动作
        btnSeasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump activity_seasons.xml
                Intent intent=new Intent(LoginActivity.this,FrameLayoutActivity.class);
                startActivity(intent);
            }
        });

//        btn_my_home按钮动作
        btnMyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jump activity_main.xml
                Intent intent =new Intent(LoginActivity.this,MyMainActivity.class);
                startActivity(intent);
                // 关闭登录界面（点击返回按键不会再回到登陆界面）
//                LoginActivity.this.finish();
            }
        });


        edit_xm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("login","onTextChanged:"+s);//打印日志
            }

            @Override
            public void afterTextChanged(Editable s) {//Editable是文本框输入的内容的数据类型    这是 s=edit_xm.getText()
                Log.d("login","afterTextChanged:"+s.toString());//打印日志
            }
        });
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 选中状态时需要执行的代码
                } else {
                    //未选中状态时需要执行的代码
                }
            }
        });

    }
    class LoginThread extends Thread{
        @Override
        public void run(){


//
//            String userType="学生";
////                ----------方法1：使用RadioButton-------------
////                if(rb_js.isChecked()){
////                    userType="教师";
////                }
////                -------方法2:使用RadioGroup----------------
//            int rb_id=radioGroup.getCheckedRadioButtonId();
//            if(rb_id==rb_js.getId()){
//                userType="教师";
//            }
//
//            Intent intent = new Intent(LoginActivity.this, StudyMainActivity.class);
//            startActivity(intent);
//            progressDialog.dismiss();//delete


            xm=edit_xm.getText().toString();
            mm=edit_mm.getText().toString();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message=new Message();


            if(xm.equals("123")){
                if(mm.equals("123")){//login succeed
                    message.what=1;
                }else message.what=2;//wrong password

            }else message.what=3;//wrong username
            handler.sendMessage(message);
            super.run();
        }
    }
    @Override
    protected void onStart() {
        Log.d("smzg","onStart:");
        super.onStart();
    }

//    会自动刷新
    @Override
    protected void onResume() {
        Log.d("smzg","onResume:");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("smzg","onDestroy:");
        super.onDestroy();
    }

    void fullScreenTool(){
        Window window = getWindow();
//        请求进行全屏布局+更改状态栏字体颜色
        //          获取程序是不是夜间模式
        int uiMode = getApplicationContext().getResources().getConfiguration().uiMode;
        if ((uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES){
//            SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  and  SYSTEM_UI_FLAG_LAYOUT_STABLE请求进行全屏布局
//            SYSTEM_UI_FLAG_VISIBLE进行更改状态栏字体颜色
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_VISIBLE);//白色
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色

        }
//                让内容显示在系统栏的后面,也就是显示在状态栏和导航栏的后面
        WindowCompat.setDecorFitsSystemWindows(window, true);
//      沉浸状态栏(给任务栏上透明的色)(Android 10 上，只需要将系统栏颜色设为完全透明即可:)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
//                沉浸导航栏（设置透明色）
        window.setNavigationBarColor(Color.TRANSPARENT);

//                在安卓10以上禁用系统栏视觉保护。
// 当设置了  导航栏 栏背景为透明时，NavigationBarContrastEnforced 如果为true，则系统会自动绘制一个半透明背景
// 状态栏的StatusBarContrast 效果同理，但是值默认为false，因此不用设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
        }

//        处理视觉冲突
        LinearLayout linearBottom=findViewById(R.id.linear_bottom);
        linearBottom.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    v.setPadding(v.getPaddingLeft(),v.getPaddingTop(),v.getPaddingRight(),insets.getSystemWindowInsets().bottom);
                }
                return insets;
            }

        });
        LinearLayout linearTop=findViewById(R.id.linear_top);
        linearTop.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    v.setPadding(v.getPaddingLeft(),insets.getSystemWindowInsets().top,v.getPaddingRight(),v.getPaddingBottom());
                }
                return insets;
            }

        });
    }
}