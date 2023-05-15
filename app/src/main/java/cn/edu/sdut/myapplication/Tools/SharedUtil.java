package cn.edu.sdut.myapplication.Tools;

import android.content.Context;
import android.content.SharedPreferences;

//通过SharedPreferences 实现 判断是否是第一次打开软件
public class SharedUtil {
    private  static SharedUtil mUtil;
    private SharedPreferences preferences;
    public static SharedUtil getInstance(Context ctx){
        if(mUtil==null){
            mUtil=new SharedUtil();
            mUtil.preferences=ctx.getSharedPreferences("shopping",Context.MODE_PRIVATE);
        }
        return mUtil;
    }
    public void writeBoolean(String key,boolean value){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public boolean readBoolean(String key,boolean defaultValue){
       return preferences.getBoolean(key, defaultValue);
    }
}