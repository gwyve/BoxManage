package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bean.Goods;
import database.DBManager;

public class GoodsAddActivity extends AppCompatActivity {

    EditText idText;
    EditText vendorText;
    EditText modelText;
    EditText typeText;
    EditText memoText;
    Button confirmBtn;
    Button resetBtn;

    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);

        idText = (EditText)findViewById(R.id.goodsAddActIDEditText);
        vendorText = (EditText)findViewById(R.id.goodsAddActVendorEditText);
        modelText = (EditText)findViewById(R.id.goodsAddActModelEditText);
        typeText = (EditText)findViewById(R.id.goodsAddActTypeEditText);
        memoText = (EditText)findViewById(R.id.goodsAddActMemoEditText);
        confirmBtn = (Button)findViewById(R.id.goodsAddActConfirmButton);
        resetBtn = (Button)findViewById(R.id.goodsAddActResetButton);

        dbm = new DBManager(this);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idText.setText("");
                vendorText.setText("");
                modelText.setText("");
                typeText.setText("");
                memoText.setText("");
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods goods = new Goods(idText.getText().toString(),vendorText.getText().toString(),modelText.getText().toString(),
                        typeText.getText().toString(),memoText.getText().toString());
                goods = dbm.addGoods(goods);
                dialog("添加成功");
            }
        });





    }


    protected void dialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsAddActivity.this);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GoodsAddActivity.this.finish();
            }
        });
        builder.create().show();
    }
}
