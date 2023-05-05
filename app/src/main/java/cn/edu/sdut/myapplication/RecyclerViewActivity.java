package cn.edu.sdut.myapplication;

import android.app.Application;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<HashMap<String,String>> data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acativity_recycler);
        data=new ArrayList<>();
        data=getData();
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        MyRecyclerViewAdapter myRecyclerViewAdapter=new MyRecyclerViewAdapter(this,data);

    }
    protected ArrayList<HashMap<String,String>> getData(){
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            HashMap<String,String> map=new HashMap<>();
            map.put("xm","张三"+i);
            map.put("dh","12344"+i);
            map.put("dz","山东理工大学"+i);
            map.put("sfz","2222"+i);
            list.add(map);
        }
        return list;
    }
}
