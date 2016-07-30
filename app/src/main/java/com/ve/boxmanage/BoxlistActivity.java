package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import Util.Util;

import Compent.ViewForBoxlistAct;
import Util.ExcelUtil;
import bean.Box;
import bean.Person;
import database.DBManager;

public class BoxlistActivity extends AppCompatActivity {

    Button backBtn;
    Button exportBtn;
    TableLayout tableLayout;

    DBManager dbm;
    List<Box> boxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxlist);

        backBtn = (Button)findViewById(R.id.boxListActBackBtn);
        exportBtn = (Button) findViewById(R.id.boxListActExportBtn);
        tableLayout = (TableLayout)findViewById(R.id.boxListActTableLayout);
        dbm = new DBManager(this);
        boxList = dbm.queryBoxGroupByType();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxlistActivity.this.finish();
            }
        });

        List<ButtonData> dataList = getData(boxList);
        TableRow tableRow = null;
        for (int i = 0; i < dataList.size();i++){
            if (i%5 == 0){
                tableRow = new TableRow(this);
                tableRow.setPadding(0,0,0,12);
            }
            final ButtonData data = dataList.get(i);
            ViewForBoxlistAct view = new ViewForBoxlistAct(BoxlistActivity.this,data);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BoxlistActivity.this,BoxContentActivity.class);
                    intent.putExtra("boxid",data.boxid);
                    startActivity(intent);
                }
            });
            tableRow.addView(view);
            if (i%5 ==4 ){
                tableLayout.addView(tableRow);
            }
        }

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Box> list = dbm.queryBox();
                Collections.sort(list);
                String fileName = ExcelUtil.boxExport(Environment.getExternalStorageDirectory().toString(), "箱柜列表_"+ Util.getDataFormat().format(new Date(System.currentTimeMillis()))+".xls", list);
                exportCompleteDialog(fileName);
            }
        });

        ActivityManagerApplication.addDestoryActivity(BoxlistActivity.this, "BoxlistAct");
    }

    private List<ButtonData> getData(List<Box> boxList){
        List<ButtonData> dataList = new LinkedList<ButtonData>();
        for (int i = 1; i<=getResources().getInteger(R.integer.box_total_number ); i++)
            dataList.add(new ButtonData(i));
        for (int i =0; i <boxList.size();i++)
            dataList.get(Integer.parseInt(boxList.get(i).getBox())-1).addTitle(boxList.get(i).getType() +"×" +boxList.get(i).getNumber());
        return dataList;
    }


    public class ButtonData{
        private int boxid;
        private String title ="";
        int titleNum=0;
        public ButtonData(int boxid){
            this.boxid = boxid;
        }
        public void addTitle(String title){
            if (titleNum < 4){
                this.title += title+"\n";
                titleNum ++;
            }else if (titleNum == 4){
                this.title += "……";
            }
        }
        public int getBoxid(){
            return boxid;
        }
        public String getTitle(){
            return title;
        }
    }


    protected  void exportCompleteDialog(String path){

        final AlertDialog dialog = new AlertDialog.Builder(BoxlistActivity.this).create();
        LayoutInflater inflater = LayoutInflater.from(BoxlistActivity.this);
        View view = inflater.inflate(R.layout.compent_for_dialog_export, null);
        dialog.setView(view);

        Button cancelBtn = (Button)view.findViewById(R.id.alertDialogConcelBtn);
        Button confirmBtn = (Button)view.findViewById(R.id.alertDialogConfirmBtn);
        TextView textView = (TextView)view.findViewById(R.id.alertDialogText);

        textView.setText("文件成功导出，路径为\n \""+ path + "\" ");
        cancelBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BoxlistActivity.this.finish();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(758, 374);


    }


}
