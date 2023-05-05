package cn.edu.sdut.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> data;
    public MyRecyclerViewAdapter(Context context, ArrayList<HashMap<String,String>> data){
        this.context=context;
        this.data=data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.itemlayout,null);//help read View
        MyViewHolder holder=new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String,String> map=data.get(position);

        holder.item_xm.setText(map.get("xm"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item_xm;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_xm=itemView.findViewById(R.id.item_xm);
        }
    }
}
