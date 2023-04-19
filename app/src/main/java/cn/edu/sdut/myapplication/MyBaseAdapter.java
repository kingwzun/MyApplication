package cn.edu.sdut.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> data;
    public MyBaseAdapter(Context context, ArrayList<HashMap<String,String>> data){
        this.context=context;
        this.data=data;
    }
    //adapter显示几条记录，一般就是ArrayList‘s size
    @Override
    public int getCount() {
        return data.size();
    }
    // ArrayList’s
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    //一般忽略
    @Override
    public long getItemId(int position) {
        return 0;
    }
//   core 每执行一次生成的一条记录
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        HashMap<String,String> map=data.get(position);
//        View view1= LayoutInflater.from(context).inflate(R.layout.itemlayout,null);//help read View
//        // map's data to View
//        TextView text_xm=view1.findViewById(R.id.item_xm);
//        TextView text_dh=view1.findViewById(R.id.item_dh);
//        TextView text_dz=view1.findViewById(R.id.item_dz);
////        处理btn焦点问题
//        Button btn_delete =view1.findViewById(R.id.btn_delete);
//        text_xm.setText(map.get("xm"));
//        text_dh.setText(map.get("dh"));
//        text_dz.setText(map.get("dz"));
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                data.remove(position);
//                notifyDataSetChanged();//re flash
//                Toast.makeText(context, map.get("xm"), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return view1;
//    }
    //优化getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String,String> map=data.get(position);
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itemlayout,null);//help read View
            holder=new ViewHolder();
            // map's data to View
            holder.txt_xm=convertView.findViewById(R.id.item_xm);
            holder.txt_dh=convertView.findViewById(R.id.item_dh);
            holder.txt_dz=convertView.findViewById(R.id.item_dz);
            holder.btn_del =convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);//view with holder
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.txt_xm.setText(map.get("xm"));
        holder.txt_dh.setText(map.get("dh"));
        holder.txt_dz.setText(map.get("dz"));
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyDataSetChanged();//re flash
                Toast.makeText(context, map.get("xm"), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView txt_xm;
        TextView txt_dh;
        TextView txt_dz;
        Button btn_del;
    }
}
