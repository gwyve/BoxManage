package com.ve.boxmanage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {

    Button takeoutBtn;
    Button putinBtn;
    Button listBtn;
    Button recordBtn;
    Button logoutBtn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        takeoutBtn = (Button)findViewById(R.id.chooseActTakeoutButton);
        putinBtn = (Button)findViewById(R.id.chooseActPutinButton);
        listBtn = (Button)findViewById(R.id.chooseActBoxListButton);
        recordBtn = (Button)findViewById(R.id.chooseActRecordButton);
        logoutBtn = (Button)findViewById(R.id.chooseActlogoutButton);

        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_WORLD_WRITEABLE);

        takeoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,TakeoutActivity.class);
                startActivity(intent);
            }
        });

        putinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,PutinActivity.class);
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

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
                ChooseActivity.this.finish();
            }
        });
    }
}
