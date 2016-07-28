package com.ve.boxmanage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import bean.Person;

public class ChooseActivity extends AppCompatActivity {

    Button takeoutBtn;
    Button putinBtn;
    Button listBtn;
    Button recordBtn;
    Button logoutBtn;

    SharedPreferences sharedPreferences;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        takeoutBtn = (Button)findViewById(R.id.chooseActTakeoutButton);
        putinBtn = (Button)findViewById(R.id.chooseActPutinButton);
        listBtn = (Button)findViewById(R.id.chooseActBoxListButton);
        recordBtn = (Button)findViewById(R.id.chooseActRecordButton);
        logoutBtn = (Button)findViewById(R.id.chooseActlogoutButton);

        sharedPreferences = this.getSharedPreferences("BOXMANAGE", MODE_PRIVATE);

        person = new Person(Long.valueOf(sharedPreferences.getLong("Person_id",-1)),sharedPreferences.getString("PersonName",null));

        takeoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        putinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, PutinActivity.class);
                startActivity(intent);
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,BoxlistActivity.class);
                startActivity(intent);
            }
        });

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setTextSize(36);
        logoutBtn.setText("登出 ("+person.getName()+")");
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
                ChooseActivity.this.finish();
            }
        });
    }


}
