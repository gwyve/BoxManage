package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Compent.ItemForRecordAct;
import Util.ExcelUtil;
import Util.Util;
import bean.Item;
import database.DBManager;

public class RecordActivity extends AppCompatActivity {


    Button backBtn;
    Button exportBtn;
    LinearLayout tableLayout;
    Button previousPageBtn;
    Button nextPageBtn;
    EditText editText;

    List<Item> dataList;
    DBManager dbm;

    int currentPage;
    int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        backBtn = (Button)findViewById(R.id.recordActBackBtn);
        exportBtn = (Button)findViewById(R.id.recordActExportBtn);
        tableLayout = (LinearLayout) findViewById(R.id.recordActTableLayout);
        previousPageBtn = (Button)findViewById(R.id.recordActPreviousPageBtn);
        nextPageBtn = (Button)findViewById(R.id.recordActNextPageBtn);
        editText = (EditText)findViewById(R.id.recordActPageEditView);

        dbm = new DBManager(this);
        currentPage = 1;
        totalPage = (int) Math.ceil((double)dbm.getItemCount()/getResources().getInteger(R.integer.page_size));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordActivity.this.finish();
                overridePendingTransition(R.anim.choose_act_return, R.anim.record_act_out);
            }
        });
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = ExcelUtil.itemExport(Environment.getExternalStorageDirectory().toString(), "存取记录_"+ Util.getDataFormat().format(new Date(System.currentTimeMillis()))+".xls", dbm.getItem());
                exportCompleteDialog(fileName);
            }
        });

        fresh();
        editText.setText(currentPage + "/" + totalPage);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    String input = editText.getText().toString();
                    if (input.matches(".*/.*")) {
                        if (Util.isNumeric((input.split("/"))[0])) {
                            currentPage = Integer.parseInt((input.split("/"))[0]);
                            fresh();
                            closeKeyBoard(RecordActivity.this, editText);
                        }
                    } else if (Util.isNumeric(input)) {
                        currentPage = Integer.parseInt(input);
                        fresh();
                        closeKeyBoard(RecordActivity.this, editText);
                    }
                    return true;
                }
                return false;
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    editText.setText("");
                }
            }
        });

        previousPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1){
                    currentPage --;
                    fresh();
                }
            }
        });
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < totalPage){
                    currentPage ++;
                    fresh();
                }
            }
        });

    }


    private void fresh(){

        dataList = dbm.getItem(getResources().getInteger(R.integer.page_size),currentPage);
        editText.setText(currentPage + "/" + totalPage);
        tableLayout.removeAllViews();
        for (int i=0;i<dataList.size();i++){
            Item item = dataList.get(i);
            ItemForRecordAct tableRow= new ItemForRecordAct(RecordActivity.this,item);
            tableLayout.addView(tableRow);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        backBtn.setFocusable(true);
        backBtn.setFocusableInTouchMode(true);
    }

    //关闭键盘
    private void closeKeyBoard(Context context, View editText){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }



    protected  void exportCompleteDialog(String path){

        final AlertDialog dialog = new AlertDialog.Builder(RecordActivity.this).create();
        LayoutInflater inflater = LayoutInflater.from(RecordActivity.this);
        View view = inflater.inflate(R.layout.compent_for_dialog_export, null);
        dialog.setView(view);

        Button cancelBtn = (Button)view.findViewById(R.id.alertDialogConcelBtn);
        Button confirmBtn = (Button)view.findViewById(R.id.alertDialogConfirmBtn);
        TextView textView = (TextView)view.findViewById(R.id.alertDialogText);

        textView.setText("文件成功导出，路径为 \n \""+ path + "\" ");
        cancelBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                RecordActivity.this.finish();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(758, 374);

    }
}
