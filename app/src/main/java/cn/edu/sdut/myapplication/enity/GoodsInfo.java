package cn.edu.sdut.myapplication.enity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.edu.sdut.myapplication.R;

public class GoodsInfo {
    public   int id;
    public String name;
    public String description;
    public float price;
    public  String picPath;//图片路径
    public  int pic;//图片编号

    // make data
    private static String[] mNameArray={"iPhone","SAMSUNG","SAMSUNG","SAMSUNG","SAMSUNG","SAMSUNG"};
    private static String[] mDescArray={"iPhone2222222",
                                        "SAMSUNG22222222",
                                        "SAMSUNG222",
                                        "SAMSUNG222",
                                        "SAMSUNG22",
                                        "SAMSUNG22"};
    private static float[] mPriceArray={222,222,222,222,222,222};
    private static int[] mPicArray={
            R.drawable.ludashi,R.drawable.ludashi,R.drawable.ludashi,
            R.drawable.ludashi,R.drawable.ludashi,R.drawable.ludashi
    };

    //获取信息列表
    public static ArrayList<GoodsInfo> getDefaultList(){
        ArrayList<GoodsInfo> list=new ArrayList<>();
        for (int i=0;i<mNameArray.length;i++){
            GoodsInfo info=new GoodsInfo();
            info.id=i;
            info.name=mNameArray[i];
            info.description=mDescArray[i];
            info.price=mPriceArray[i];
            info.pic=mPicArray[i];
            list.add(info);
        }
        return list;
    }

}
