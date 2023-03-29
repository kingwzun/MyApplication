package cn.edu.sdut.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_login);
        Button btnLogin=findViewById(R.id.btnLogin);
        EditText edit_xm=findViewById(R.id.edit_xm);
        EditText edit_mm = findViewById(R.id.edit_mm);
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
}