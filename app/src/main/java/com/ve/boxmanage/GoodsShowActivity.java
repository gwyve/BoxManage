package com.ve.boxmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bean.Box;
import bean.Goods;

public class GoodsShowActivity extends AppCompatActivity {

    Button backBtn;
    Button nextBtn;
    TextView boxidTextView;
    TextView vendorTextView;
    TextView modelTextView;
    TextView typeTextView;
    TextView memoTextView;
    Box box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_show);

        backBtn = (Button) findViewById(R.id.goodsShowActBackBtn);
        nextBtn = (Button)findViewById(R.id.goodsShowActNextBtn);
        boxidTextView = (TextView)findViewById(R.id.goodsShowActBoxidTextView);
        vendorTextView = (TextView)findViewById(R.id.goodsShowActVendorTextView);
        modelTextView = (TextView)findViewById(R.id.goodsShowActModelTextView);
        typeTextView = (TextView)findViewById(R.id.goodsShowActTypeTextView);
        memoTextView = (TextView)findViewById(R.id.goodsShowActMemoTextView);


        box = (Box)getIntent().getSerializableExtra("box");

        if (Integer.parseInt(box.getBox())>9){
            boxidTextView.setText(box.getBox());
        }else {
            boxidTextView.setText("  "+box.getBox());
        }
        vendorTextView.setText(box.getVendor());
        modelTextView.setText(box.getModel());
        typeTextView.setText(box.getType());
        memoTextView.setText(box.getMemo());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsShowActivity.this.finish();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsShowActivity.this,TakeoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("box",box);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ActivityManagerApplication.addDestoryActivity(GoodsShowActivity.this,"GoodsShowAct");
    }
}
