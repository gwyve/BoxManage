package com.ve.boxmanage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bean.Item;
import bean.Person;

public class PutinActivity extends AppCompatActivity {

    TextView titleText;
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

        titleText = (TextView)findViewById(R.id.putinActLeftTopTitleTextView);
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

        titleText.setText(person.getName()+"放物  >  添加物品信息 ");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutinActivity.this.finish();
                overridePendingTransition(R.anim.choose_act_return, R.anim.putin_act_out);
            }
        });
        backBtn.setFocusable(true);
        backBtn.requestFocus();

        vendorText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && vendorText.getText().toString().equals("必填")) {
                    vendorText.setText("");
                    vendorText.setTextColor(getResources().getColor(R.color.blackColor));
                }
            }
        });
        vendorText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isOK();
            }
        });

        modelText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && modelText.getText().toString().equals("必填")) {
                    modelText.setText("");
                    modelText.setTextColor(getResources().getColor(R.color.blackColor));
                }
            }
        });
        modelText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isOK();
            }
        });

        typeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && typeText.getText().toString().equals("必填")) {
                    typeText.setText("");
                    typeText.setTextColor(getResources().getColor(R.color.blackColor));
                }

            }
        });
        typeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isOK();
            }
        });

        memoText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && memoText.getText().toString().equals("备注")) {
                    memoText.setText("");
                    memoText.setTextColor(getResources().getColor(R.color.blackColor));
                }
            }
        });
        memoText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    memoText.setText(memoText.getText().toString().substring(0, memoText.getText().toString().length() - 1));
                    closeKeyBoard(PutinActivity.this, memoText);
                    return true;
                }
                return false;
            }
        });

        numberText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isOK();
            }
        });

        subBtn.setClickable(false);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberText.getText().toString());
                if (number == 2 || number == 1) {
                    subBtn.setClickable(false);
                    numberText.setText(1 + "");
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
                if (memoText.getText().toString().equals("备注")){
                    item = new Item(vendorText.getText().toString(), modelText.getText().toString(), typeText.getText().toString(),
                            "", person, "putin", Integer.parseInt(numberText.getText().toString()));
                }else {
                    item = new Item(vendorText.getText().toString(), modelText.getText().toString(), typeText.getText().toString(),
                            memoText.getText().toString(), person, "putin", Integer.parseInt(numberText.getText().toString()));
                }
                Intent intent = new Intent(PutinActivity.this, BoxChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
        nextBtn.setClickable(false);
        ActivityManagerApplication.addDestoryActivity(PutinActivity.this, "PutinAct");


    }

    @Override
    protected void onResume(){
        super.onResume();
        closeKeyBoard(this, vendorText);
        closeKeyBoard(this, modelText);
        closeKeyBoard(this, typeText);
        closeKeyBoard(this, memoText);
        backBtn.setFocusable(true);
        backBtn.setFocusableInTouchMode(true);
    }



    private boolean isOK(){
        if (!vendorText.getText().toString().equals("") && !vendorText.getText().toString().equals("必填")
                && !modelText.getText().toString().equals("") && !modelText.getText().toString().equals("必填")
                && !typeText.getText().toString().equals("") && !typeText.getText().toString().equals("必填")
                && !numberText.getText().toString().equals("") && Integer.parseInt(numberText.getText().toString())>0){
            nextBtn.setBackgroundResource(R.drawable.putin_act_next_btn_2);
            nextBtn.setClickable(true);
            return true;
        }else{
            nextBtn.setBackgroundResource(R.drawable.putin_act_next_btn_1);
            nextBtn.setClickable(false);
            return false;
        }
    }

    //关闭键盘
    private void closeKeyBoard(Context context, View editText){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


}
