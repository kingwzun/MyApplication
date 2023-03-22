package cn.edu.sdut.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FrameLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    Button butSpring,butSummer;
    ImageView imgSpring,imgSummer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();

    }
    protected void initView(){
        butSpring=findViewById(R.id.butSpring);
        butSummer=findViewById(R.id.butSummer);
        imgSpring=findViewById(R.id.imgSpring);
        imgSummer=findViewById(R.id.imgSummer);
    }
    protected void initListener(){
        butSummer.setOnClickListener(this::onClick);
        butSpring.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        imgSummer.setVisibility(View.INVISIBLE);
        imgSpring.setVisibility(View.INVISIBLE);
        switch (id){
            case R.id.butSpring:
                imgSpring.setVisibility(View.VISIBLE);
                break;
            case R.id.butSummer:
                imgSummer.setVisibility(View.VISIBLE);
        }
    }
}
