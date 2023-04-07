package cn.edu.sdut.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FrameLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSpring,btnSummer;
    ImageView imgSpring,imgSummer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);
        initView();
        initListener();
    }
    protected void initView(){
        btnSpring=findViewById(R.id.btn_spring);
        btnSummer=findViewById(R.id.btn_summer);
        imgSpring=findViewById(R.id.img_spring);
        imgSummer=findViewById(R.id.img_summer);
    }
    protected void initListener(){
        btnSummer.setOnClickListener(this::onClick);
        btnSpring.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View view) {
        int id=view.getId();
        imgSummer.setVisibility(View.INVISIBLE);
        imgSpring.setVisibility(View.INVISIBLE);
        switch (id){
            case R.id.btn_spring:
                imgSpring.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_summer:
                imgSummer.setVisibility(View.VISIBLE);
        }
    }
}
