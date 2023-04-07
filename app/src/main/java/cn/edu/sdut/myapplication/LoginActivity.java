package cn.edu.sdut.myapplication;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.color.DynamicColors;

import java.io.LineNumberReader;
import java.lang.reflect.Field;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.constraint_login);

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

//      沉浸状态栏(给任务栏上透明的色)(Android 10 上，只需要将系统栏颜色设为完全透明即可:)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.all_alpha));
        }

//                沉浸导航栏
//        让内容显示在系统栏的后面,也就是显示在状态栏和导航栏的后面
//        WindowCompat.setDecorFitsSystemWindows(window, true);

        window.setNavigationBarColor(Color.TRANSPARENT);
//  在安卓10以上禁用系统栏视觉保护。
// 当设置了  导航栏 栏背景为透明时，NavigationBarContrastEnforced 如果为true，则系统会自动绘制一个半透明背景
// 状态栏的StatusBarContrast 效果同理，但是值默认为false，因此不用设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
        }

//        处理视觉冲突
        LinearLayout constraintBottom=findViewById(R.id.constraint_bottom_btm);
        constraintBottom.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    v.setPadding(v.getPaddingLeft(),v.getPaddingTop(),v.getPaddingRight(),insets.getSystemWindowInsets().bottom);
                }
                return insets;
            }

        });
       LinearLayout linear_top=findViewById(R.id.linear_top);
        linear_top.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    v.setPadding(v.getPaddingLeft(),insets.getSystemWindowInsets().top,v.getPaddingRight(),v.getPaddingBottom());
                }
                return insets;
            }

        });


        Button btnLogin=findViewById(R.id.btn_my_login);
       btnLogin.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
           @NonNull
           @Override
           public WindowInsets onApplyWindowInsets(@NonNull View v, @NonNull WindowInsets insets) {
               return null;
           }
       });

        Button btnView=findViewById(R.id.btnViewDome);
        Button btnSeasons=findViewById(R.id.btnSeasonDome);
        Button btnMyHome=findViewById(R.id.btn_my_home);
//        Button btnMyLogin=findViewById(R.id.btn_my_login);

        EditText edit_xm=findViewById(R.id.edit_login);
        EditText edit_mm = findViewById(R.id.edit_register);
        ToggleButton toggle= findViewById(R.id.TgBtm);
        RadioButton rb_js= findViewById(R.id.rb_js);
        RadioButton rb_xs= findViewById(R.id.rb_xs);
        RadioGroup radioGroup=findViewById(R.id.usertype);
        CheckBox checkbox=findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(LoginActivity.this,"记住密码", Toast.LENGTH_LONG);
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
                String userType="学生";
//                ----------方法1：使用RadioButton-------------
//                if(rb_js.isChecked()){
//                    userType="教师";
//                }
//                -------方法2:使用RadioGroup----------------
                int rb_id=radioGroup.getCheckedRadioButtonId();
                if(rb_id==rb_js.getId()){
                    userType="教师";
                }
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
//               关闭登录界面（点击返回按键不会再回到登陆界面）
                LoginActivity.this.finish();
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

//        btnMyLogin
//        btnMyLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //jump activity_test.xml
//                Intent intent =new Intent(LoginActivity.this,MyLoginActivity.class);
//                startActivity(intent);
//            }
//        });

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




}