package cn.edu.sdut.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdut.myapplication.enity.CartInfo;
import cn.edu.sdut.myapplication.enity.GoodsInfo;

public class ShoppingDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shopping.db";   //数据库名称
    private SQLiteDatabase mWDB = null;//数据库实例
    private SQLiteDatabase mRDB = null;//数据库实例
    //商品信息表
    private static String TABLE_GOODS_INFO="goods_info";
    //购物车信息表
    private static String TABLE_CART_INFO="cart_info";
    private static final int DB_VERSION = 1;   //数据库的版本号

    private static ShoppingDBHelper helper = null;   //单例


    public ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public ShoppingDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    //通过单例模式获取 UserDBHelper 的唯一实例
    public static synchronized ShoppingDBHelper getInstance(Context context) {
      if (helper == null) {
            helper = new ShoppingDBHelper(context);
        }

        return helper;
    }

    //打开读连接
    public SQLiteDatabase openReadLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = helper.getReadableDatabase();
        }

        return mWDB;
    }
//
    //打开写连接
    public SQLiteDatabase openWriteLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = helper.getWritableDatabase();
        }

        return mRDB;
    }

    //关闭数据库连接
    public void closeLink() {
        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    //创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        //先删除已存在表
        String drop_sql = "drop table if exists " + TABLE_GOODS_INFO + ";";
        db.execSQL(drop_sql);

        //创建商品信息表
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS_INFO
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name VARCHAR NOT NULL,"
                + "description VARCHAR NOT NULL,"
                + "price FLOAT NOT NULL,"
                + "pic_path VARCHAR NOT NULL"
                + ");";
        db.execSQL(create_sql);

        //创建购物车信息表
        create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART_INFO
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "goods_id INTEGER NOT NULL,"
                + "count INTEGER NOT NULL"
                + ");";
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    //修改表结构
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (newVersion > 1) {
//            //Android的ALTER命令不支持一次添加多列，只能分多次添加
//            String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN phone VARCHAR;";
//            db.execSQL(alter_sql);
//
//            alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
//            db.execSQL(alter_sql); // 执行完整的SQL语
//        }
//    }
//
//    //根据指定条件删除记录
//    public int delete(String condition) {
//        return sdb.delete(TABLE_NAME, condition, null);
//    }
//
//    //删除全部记录
//    public int deleteAll() {
//        return sdb.delete(TABLE_NAME, "1=1", null);
//    }
//
//    //根据条件查询记录
//    public List<UserInfo> query(String condition) {
//        String sql = String.format("select rowid,_id,name,age,height,weight,married,update_time," +
//                "phone,password from %s where %s;", TABLE_NAME, condition);
//        //执行查询语句，该语句返回结果集的游标
//        Cursor cursor = sdb.rawQuery(sql, null);
//
//        ArrayList<UserInfo> userInfos = new ArrayList<>();
//
//        //循环取出游标指向的结果集
//        while (cursor.moveToNext()) {
//            UserInfo userInfo = new UserInfo();
//            userInfo.name = cursor.getString(2);
//            userInfo.age = cursor.getInt(3);
//            userInfos.add(userInfo);
//        }
//
//        cursor.close();
//        return userInfos;
//    }

    //添加商品到购物车
    public void insertGoodsInfo(int goodsId) {
        CartInfo cartInfo=queryCartInfoByGoodsId(goodsId);
        //如果不存在添加商品
        ContentValues values=new ContentValues();
        values.put("goods_id",goodsId);
        if(cartInfo==null){
            values.put("count",1);
            mWDB.insert(TABLE_CART_INFO,null,values);
        }else{
            //如果存在更新数量
            values.put("_id",cartInfo.id);
            values.put("count",++cartInfo.count);
            mWDB.update(TABLE_CART_INFO,values,"_id=?" ,new String[]{String.valueOf(cartInfo.id)});

        }

    }

    //根据商品信息查询购物车信息
    private CartInfo queryCartInfoByGoodsId(int goodsId) {
        Cursor cursor=mRDB.query(TABLE_CART_INFO,null,"goods_id=?",new String[]{String.valueOf(goodsId)},null,null,null);
        CartInfo info=null;
        if(cursor.moveToNext()){
            info=new CartInfo();
            info.id=cursor.getInt(0);
            info.goodsId=cursor.getInt(1);
            info.count=cursor.getInt(2);
        }
        return info;
    }

    //往表里添加多条记录
    public void insertGoodsInfos(List<GoodsInfo> list) {
        //事务
        try{
            mWDB.beginTransaction();
            for (GoodsInfo info:list){
                ContentValues values    =new ContentValues();
                values.put("name",info.name);
                values.put("description",info.description);
                values.put("price",info.price);
                values.put("pic_path",info.picPath);
                mWDB.insert(TABLE_GOODS_INFO,null,values);
            }
            mWDB.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            mWDB.endTransaction();
        }

    }

    //select all goods
    public List<GoodsInfo> queryAllGoodsInfo() {
        String sql = "select * from "+TABLE_GOODS_INFO;
        List<GoodsInfo> list = new ArrayList<>();
        //执行查询语句，该语句返回结果集的游标
        Cursor cursor = mRDB.rawQuery(sql, null);

        //循环取出游标指向的结果集
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.id=cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //查询购物车中商品总数
    public int countCartInfo() {
        int count=0;
        String sql="select sum(count) from "+TABLE_CART_INFO;
        Cursor cursor=mRDB.rawQuery(sql,null);
        if(cursor.moveToNext()){
            count=cursor.getInt(0);
        }
        return count;
    }

    //select cartInfoList
    public List<CartInfo> queryAllCartInfo() {
        List<CartInfo> list=new ArrayList<>();
        String sql = "select * from "+TABLE_CART_INFO;
        //执行查询语句，该语句返回结果集的游标
        Cursor cursor = mRDB.rawQuery(sql, null);

        //循环取出游标指向的结果集
        while (cursor.moveToNext()) {
            CartInfo  info=new CartInfo();
            info.id=cursor.getInt(0);
            info.goodsId=cursor.getInt(1);
            info.count=cursor.getInt(2);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //根据商品编号查询商品数据 on GoodsDatabase
    public GoodsInfo queryGoodsInfoById(int goodsId) {
        GoodsInfo info=null;

        Cursor cursor=mRDB.query(TABLE_GOODS_INFO,null," _id=? ",new String[]{String.valueOf(goodsId)},null,null,null);
        if(cursor.moveToNext()){
            info =new GoodsInfo();
            info.id=cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
        }
        return info;
    }

    public void deleteCartInfoByGoodsId(int goodsId) {
        //从购物车删除商品
        mWDB.delete(TABLE_CART_INFO,"goods_id=?",new String[]{String.valueOf(goodsId)});
    }
    public void deleteAllCartInfo(int goodsId) {
        mWDB.delete(TABLE_CART_INFO,"1=1",null);
    }

//    //根据指定条件更新表记录
//    public int update(UserInfo userInfo, String condition) {
//
//        ContentValues cv = new ContentValues();
//        cv.put("name", userInfo.name);
//        cv.put("age", userInfo.age);
//
//        return sdb.update(TABLE_NAME, cv, condition, null);
//    }
}
