package com.ve.boxmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.Box;
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
        boxList = dbm.queryBox();

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
            }
            final ButtonData data = dataList.get(i);
            Button button = new Button(this);
            button.setText(data.boxid+"号\n"+data.title);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BoxlistActivity.this,BoxContentActivity.class);
                    intent.putExtra("boxid",data.boxid);
                    startActivity(intent);
                }
            });
            tableRow.addView(button);
            if (i%5 ==4 ){
                tableLayout.addView(tableRow);
            }
        }

    }

    private List<ButtonData> getData(List<Box> boxList){
        List<ButtonData> dataList = new LinkedList<ButtonData>();
        for (int i = 1; i<=getResources().getInteger(R.integer.box_total_number ); i++)
            dataList.add(new ButtonData(i));
        for (int i =0; i <boxList.size();i++)
            dataList.get(Integer.parseInt(boxList.get(i).getBox())-1).addTitle(boxList.get(i).getType() +"×" +boxList.get(i).getNumber());
        return dataList;
    }


    protected class ButtonData{
        int boxid;
        String title ="";
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
    }



}
