package cn.edu.sdut.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import cn.edu.sdut.myapplication.database.ShoppingDBHelper;
import cn.edu.sdut.myapplication.enity.GoodsInfo;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {
    //声明一个帮助类
    private ShoppingDBHelper mDBHelper;
    TextView tv_title,tv_count;
    GridLayout gl_channel;
    int goodsCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商场");

        tv_count=findViewById(R.id.tv_count);
        gl_channel =findViewById(R.id.gl_channel);
        findViewById( R.id.iv_back).setOnClickListener(this);
        findViewById( R.id.iv_cart).setOnClickListener(this);
        mDBHelper=ShoppingDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

        //从数据读取信息，并展示
        showGoods();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //查询商品总数并展示
        showCartInfoTotal();
    }

    //查询商品总数并展示
    private void showCartInfoTotal() {
        int count=mDBHelper.countCartInfo();
        goodsCount=count;
        tv_count.setText(String.valueOf(goodsCount));
    }

    private void showGoods() {
        // 商品条目是一个线性布局,设置布局的宽度为屏幕的一半
        int screenWidth = getResources ().getDisplayMetrics ().widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);
        //select all goods from database
        List<GoodsInfo> list = mDBHelper.queryAllGoodsInfo();
        for (GoodsInfo info : list) {
            // get layout item_goods and it's views
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);
            Button btn_add = view.findViewById(R.id.btn_add);
            //set the value of the view
            iv_thumb.setImageURI(Uri.parse(info.picPath));
            tv_name.setText(info.name);
            tv_price.setText(String.valueOf(info.price));

            //添加到购物车
            btn_add.setOnClickListener(v -> {
                addToCart(info.id,info.name);
            });
            //把商品布局添加到网格布局
            gl_channel.addView(view,params);
        }
    }

    private void addToCart(int goodsId, String name) {
        mDBHelper.insertGoodsInfo( goodsId);

        Toast.makeText(ShoppingChannelActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
        //购物车数量+1
            int count=++goodsCount;
            tv_count.setText(String.valueOf(count));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击返回页面，关闭当前页面
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:

                //点击购物车图，从商场跳到购物车
                Intent intent=new Intent(this,ShoppingCartActivity.class);
                //设置启动标记，避免多次返回同一界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }
}
