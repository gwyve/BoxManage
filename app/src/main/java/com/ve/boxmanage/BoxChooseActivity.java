package com.ve.boxmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

import bean.Goods;

public class BoxChooseActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_choose);

        textView = (TextView)findViewById(R.id.textView2);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Goods goods = (Goods)bundle.getSerializable("goods");
        textView.setText(goods.toString());

    }
}
