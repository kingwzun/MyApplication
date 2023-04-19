package cn.edu.sdut.myapplication;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewActivity  extends Activity {
    ListView listView;
    String[] course;
    ArrayList<HashMap<String,String>> data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView=findViewById(R.id.list_view);
        data=new ArrayList<>();

        course=getResources().getStringArray(R.array.course);
        data=getData();
        String[] fromKey=new String[]{"xm","dh","dz"};
        int[] toID=new int[]{R.id.item_xm,R.id.item_dh,R.id.item_dz};
        //MyAdapter
//        MyBaseAdapter myBaseAdapter=new MyBaseAdapter(this,data);
//        listView.setAdapter(myBaseAdapter);

        //SimplerAdapter
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,data,R.layout.itemlayout,fromKey,toID);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map=data.get(position);
                String sfz=map.get("sfz");
                Toast.makeText(ListViewActivity.this, "sfz", Toast.LENGTH_SHORT).show();

            }
        });
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
