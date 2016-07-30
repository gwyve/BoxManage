package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import Util.Util;
import bean.Box;
import bean.Item;
import bean.Person;
import database.DBManager;

public class TakeoutActivity extends AppCompatActivity {

    TextView titleText;
    Button backBtn;
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

        titleText = (TextView)findViewById(R.id.takeoutActLeftTopTitleTextView);
        backBtn = (Button) findViewById(R.id.takeoutActBackBtn);
        confirmBtn = (Button)findViewById(R.id.takeoutActConfirmBtn);
        editText = (EditText)findViewById(R.id.takeoutActExplainEditText);
        box = (Box)getIntent().getSerializableExtra("box");

        dbm = new DBManager(this);
        sharedPreferences = this.getSharedPreferences("BOXMANAGE", MODE_PRIVATE);
        person = new Person(Long.valueOf(sharedPreferences.getLong("Person_id", -1)), sharedPreferences.getString("PersonName", null));

        titleText.setText(getIntent().getStringExtra("title")+"  >  记录去向");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeoutActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        confirmBtn.setBackgroundResource(R.drawable.takeout_act_confirm_btn_2);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(box.getVendor(), box.getModel(), box.getType(), box.getMemo(), person, "takeout", 1);
                item.setBox(box.getBox());
                item.setExplain(editText.getText().toString());
                item.setTime(Util.getDataFormat().format(new Date(System.currentTimeMillis())));
                if (dbm.takeout(item)) {
                    ActivityManagerApplication.destroyActivity("GoodsShowAct");
                    ActivityManagerApplication.destroyActivity("SearchAct");
                    ActivityManagerApplication.destroyActivity("BoxlistAct");
                    ActivityManagerApplication.destroyActivity("BoxContentAct");
                    confirmDialog();
                }

            }
        });
        confirmBtn.setClickable(false);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    editText.setText(editText.getText().toString().substring(0,editText.getText().toString().length()-1));
                    closeKeyBoard(TakeoutActivity.this,editText);
                    return true;
                }
                return false;
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && editText.getText().toString().equals("请输入使用去向")) ;
                {
                    editText.setText("");
                    editText.setTextColor(getResources().getColor(R.color.blackColor));
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editText.getText().toString().equals("") && !editText.getText().toString().equals("请输入使用去向")){
                    confirmBtn.setClickable(true);
                    confirmBtn.setBackgroundResource(R.drawable.takeout_act_confirm_btn);
                }else {
                    confirmBtn.setClickable(false);
                    confirmBtn.setBackgroundResource(R.drawable.takeout_act_confirm_btn_2);
                }
            }
        });
    }



    //关闭键盘
    private void closeKeyBoard(Context context, View editText){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    protected  void confirmDialog(){


        final AlertDialog dialog = new AlertDialog.Builder(TakeoutActivity.this).create();
        LayoutInflater inflater = LayoutInflater.from(TakeoutActivity.this);
        View view = inflater.inflate(R.layout.compent_for_dialog_export, null);
        dialog.setView(view);

        Button cancelBtn = (Button)view.findViewById(R.id.alertDialogConcelBtn);
        Button confirmBtn = (Button)view.findViewById(R.id.alertDialogConfirmBtn);
        TextView textView = (TextView)view.findViewById(R.id.alertDialogText);

        textView.setText("请取出物品");
        cancelBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                TakeoutActivity.this.finish();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(758, 374);

    }



    @Override
    protected void onResume(){
        super.onResume();
        backBtn.setFocusable(true);
        backBtn.setFocusableInTouchMode(true);
    }

}
