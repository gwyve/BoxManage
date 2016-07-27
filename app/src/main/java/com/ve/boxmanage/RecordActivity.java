package com.ve.boxmanage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

import Util.ExcelUtil;
import bean.Item;
import database.DBManager;

public class RecordActivity extends AppCompatActivity {


    Button backBtn;
    Button exportBtn;
    TableLayout tableLayout;
    Button previousPageBtn;
    Button nextPageBtn;
    EditText editText;
    TableRow titleRableRow;

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
        tableLayout = (TableLayout) findViewById(R.id.recordActTableLayout);
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
            }
        });
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExcelUtil.itemExport(Environment.getExternalStorageDirectory().toString(),"存取记录.xls",dbm.getItem());
            }
        });

        String[] titleArray = {"日期","时间","品牌","类型","型号","操作者","箱柜号","动作/去向"};
        titleRableRow = new TableRow(this);
        for(int i=0;i <titleArray.length;i++){
            TextView textView = new TextView(this);
            textView.setText(titleArray[i]);
            titleRableRow.addView(textView);
        }

        fresh();
        editText.setText(currentPage + "/" + totalPage);
        previousPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0){
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

        tableLayout.removeAllViews();
        tableLayout.addView(titleRableRow);
        for (int i=0;i<dataList.size();i++){
            Item item = dataList.get(i);
            TableRow tableRow = new TableRow(this);
            String[] times = item.getTime().split("_");
            TextView textView = new TextView(this);
            textView.setText(times[0]);
            tableRow.addView(textView);
            textView = new TextView(this);
            textView.setText(times[1]);
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setText(item.getVendor());
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setText(item.getModel());
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setText(item.getType());
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setText(item.getPersonName());
            tableRow.addView(textView);

            textView = new TextView(this);
            textView.setText(item.getBox());
            tableRow.addView(textView);

            textView = new TextView(this);
            if (item.getAction().equals("putin")){
                textView.setText("放物");
            }else {
                textView.setText(item.getExplain());
            }
            tableRow.addView(textView);
            tableLayout.addView(tableRow);
        }
    }
}
