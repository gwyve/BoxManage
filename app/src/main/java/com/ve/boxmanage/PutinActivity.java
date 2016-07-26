package com.ve.boxmanage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Util.Util;
import java.util.Date;
import bean.Item;
import bean.Person;

public class PutinActivity extends AppCompatActivity {

    Button backBtn;
    EditText vendorText;
    EditText modelText;
    EditText typeText;
    EditText memoText;
    Button subBtn;
    Button plusBtn;
    TextView numberText;
    Button nextBtn;

    Person person;
    Item item;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putin);

        backBtn = (Button) findViewById(R.id.putinActBackBtn);
        vendorText = (EditText) findViewById(R.id.putinActVendorEditText);
        modelText = (EditText) findViewById(R.id.putinActModelEditText);
        typeText = (EditText) findViewById(R.id.putinActTypeEditText);
        memoText = (EditText) findViewById(R.id.putinActMemoEditText);
        subBtn = (Button) findViewById(R.id.putinActSubBtn);
        plusBtn = (Button) findViewById(R.id.putinActPlusBtn);
        numberText = (TextView) findViewById(R.id.putinActNumberTextView);
        nextBtn = (Button) findViewById(R.id.putinActNextBtn);

        sharedPreferences = this.getSharedPreferences("BOXMANAGE", MODE_PRIVATE);

        person = new Person(Long.valueOf(sharedPreferences.getLong("Person_id", -1)), sharedPreferences.getString("PersonName", null));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutinActivity.this.finish();
            }
        });
        backBtn.setFocusable(true);
        backBtn.requestFocus();

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberText.getText().toString());
                if (number == 0 || number == 1) {
                    subBtn.setClickable(false);
                    numberText.setText(0 + "");
                } else {
                    number--;
                    numberText.setText(number + "");
                }
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberText.getText().toString());
                number++;
                subBtn.setClickable(true);
                numberText.setText(number + "");
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new Item( vendorText.getText().toString(), modelText.getText().toString(), typeText.getText().toString(),
                        memoText.getText().toString(), person, "putin", Integer.parseInt(numberText.getText().toString()));
                Intent intent = new Intent(PutinActivity.this, BoxChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ActivityManagerApplication.addDestoryActivity(PutinActivity.this,"PutinAct");

    }




}
