package cn.edu.sdut.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyMainActivity extends AppCompatActivity {
    int shijian=10;
    Spinner spinnerCourse;
    Long t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t1=System.currentTimeMillis();
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            t2=System.currentTimeMillis();
            if(t2-t1<1000){
                MyMainActivity.this.finish();
            }else{
                t1=t2;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();

            }
        }
        return true;
    }

}
