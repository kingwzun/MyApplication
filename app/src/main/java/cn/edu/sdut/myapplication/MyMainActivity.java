package cn.edu.sdut.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyMainActivity extends AppCompatActivity {
    int shijian=10;
    Spinner spinnerCourse;
    Long t1,t2;
    ImageView imageView;
//Button takPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        t1=System.currentTimeMillis();
        setContentView(R.layout.activity_main);

        //create camera to take photo
        imageView = findViewById(R.id.tak_photo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//take camera
                startActivityForResult(intent,1);//catch photo

//                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                Bitmap bitmap= (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
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
