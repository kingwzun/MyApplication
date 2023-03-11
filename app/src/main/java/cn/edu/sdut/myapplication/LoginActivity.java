package cn.edu.sdut.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Button btnLogin=findViewById(R.id.login_button);
//        btnLogin.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(LoginActivity.this,"看看这里", Toast.LENGTH_SHORT);
//                    }
//                }
//        );
    }
}