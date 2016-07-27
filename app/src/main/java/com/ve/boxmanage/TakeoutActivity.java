package com.ve.boxmanage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import Util.Util;
import bean.Box;
import bean.Item;
import bean.Person;
import database.DBManager;

public class TakeoutActivity extends AppCompatActivity {

    Button bakcBtn;
    Button confirmBtn;
    EditText editText;
    Box box;
    Person person;

    DBManager dbm;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeout);

        bakcBtn = (Button) findViewById(R.id.takeoutActBackBtn);
        confirmBtn = (Button)findViewById(R.id.takeoutActConfirmBtn);
        editText = (EditText)findViewById(R.id.takeoutActExplainEditText);
        box = (Box)getIntent().getSerializableExtra("box");

        dbm = new DBManager(this);
        sharedPreferences = this.getSharedPreferences("BOXMANAGE", MODE_PRIVATE);
        person = new Person(Long.valueOf(sharedPreferences.getLong("Person_id", -1)), sharedPreferences.getString("PersonName", null));


        bakcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeoutActivity.this.finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(box.getVendor(),box.getModel(),box.getType(),box.getMemo(),person,"takeout",1);
                item.setBox(box.getBox());
                item.setExplain(editText.getText().toString());
                item.setTime(Util.getDataFormat().format(new Date(System.currentTimeMillis())));
                if (dbm.takeout(item)){
                    ActivityManagerApplication.destroyActivity("GoodsShowAct");
                    ActivityManagerApplication.destroyActivity("SearchAct");
                    TakeoutActivity.this.finish();
                }
            }
        });

    }
}
