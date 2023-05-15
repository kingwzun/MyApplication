package cn.edu.sdut.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sdut.myapplication.database.ShoppingDBHelper;
import cn.edu.sdut.myapplication.enity.CartInfo;
import cn.edu.sdut.myapplication.enity.GoodsInfo;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_title,tv_count;
    ShoppingDBHelper mDBHelper;
    LinearLayout ll_cart, ll_empty;
    int goodsCount;

    // 声明一个购物车中的商品信息列表
    private List<CartInfo> mCartList;
    // 声明一个根据商品编号查找商品信息的映射,把商品信息缓存起来,这样不用每一次都去查询数据库
    private Map<Integer,GoodsInfo> mGoodsMap =new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_count=findViewById(R.id.tv_count);

        ll_cart= findViewById(R.id.ll_cart);
        ll_empty=findViewById(R.id.ll_empty);
        findViewById( R.id.iv_back).setOnClickListener(this);

        mDBHelper=ShoppingDBHelper.getInstance(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //查询商品总数并展示
        showCartInfoTotal();
        //查询购物车信息并展示
        showCart();
    }



    //查询购物车信息并展示
    private void showCart() {
        //移除下方的所有子视图
        ll_cart.removeAllViews();
        //select cartInfoList
        mCartList=mDBHelper.queryAllCartInfo();
        if(mCartList.size()==0){
            return;
        }

        for (CartInfo info : mCartList) {
            Log.d("smzg",String.valueOf(info.goodsId));
            //根据商品编号查询商品数据 on GoodsDatabase
           GoodsInfo goodsInfo= mDBHelper.queryGoodsInfoById(info.goodsId);
            // 声明一个根据商品编号查找商品信息的映射,把商品信息缓存起来,这样不用每一次都去查询数据库
            mGoodsMap.put(info.goodsId,goodsInfo);
            // get layout item_cart and it's views
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            TextView tv_count = view.findViewById(R.id.tv_count);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_sum = view.findViewById(R.id.tv_sum);

            //set the value of the view
            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
            tv_name.setText(goodsInfo.name);
            tv_desc.setText(goodsInfo.description);
            tv_count.setText(String.valueOf(info.count));
            tv_price.setText(String.valueOf(goodsInfo.price));
            //设置总价
            tv_sum.setText(String.valueOf(info.count*goodsInfo.price));

            //给商品行添加长按事件，长按删除商品
            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setMessage("是否从购物车删除"+goodsInfo.name+"商品?");
                 builder.setPositiveButton("是",((dialog, which) -> {
                     //移除当前界面
                     ll_cart.removeView(v);
                     //删除该商品
                     deleteGoods(info);
                 }));
                 builder.setNegativeButton("否",null);
                 builder.create().show();
                return true;
            });


            //把商品添加到商品行
            ll_cart.addView(view);
        }


    }

    private void deleteGoods(CartInfo cartInfo) {
        goodsCount-=cartInfo.count;
        //从购物车 数据库  删除商品
        mDBHelper.deleteCartInfoByGoodsId(cartInfo.goodsId);
        //从购物车 列表  删除商品

        CartInfo  removed=null;
        for (CartInfo info : mCartList) {
            if(info.goodsId==cartInfo.goodsId){
                removed=cartInfo;        //为什么不直接删除呢??   foreach过程中不允许删除
                break;

            }
        }
        mCartList.remove(removed);

        //显示最新的商品数量
        showCount();


        Toast.makeText(ShoppingCartActivity.this, "已从购物车删除"+mGoodsMap.get(cartInfo.goodsId).name, Toast.LENGTH_SHORT).show();
        mGoodsMap.remove(cartInfo.goodsId);
    }

    //显示最新的商品数量
    private void showCount() {
        tv_count.setText(String.valueOf(goodsCount));
        //  m没有商品，显示空空如也
        if (goodsCount==0){
            ll_empty.setVisibility(View.VISIBLE);
            ll_cart.removeAllViews();
        }
        else {
            ll_empty.setVisibility(View.GONE);
        }

    }

    //查询商品总数并展示
    private void showCartInfoTotal() {

        int count=mDBHelper.countCartInfo();
        goodsCount=count;
        tv_count.setText(String.valueOf(goodsCount));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击返回页面，关闭当前页面
            case R.id.iv_back:
                finish();
                break;


        }
    }
}
