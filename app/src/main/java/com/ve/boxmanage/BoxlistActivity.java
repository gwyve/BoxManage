package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.PowerManager;
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
import Util.DownloadServer;
import database.DBManager;

public class BoxlistActivity extends AppCompatActivity {

    TextView titleText;
    Button backBtn;
    Button exportBtn;
    TableLayout tableLayout;

    DBManager dbm;
    List<Box> boxList;

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxlist);

        titleText = (TextView)findViewById(R.id.boxlistActLeftTopTitleTextView);
        backBtn = (Button)findViewById(R.id.boxListActBackBtn);
        exportBtn = (Button) findViewById(R.id.boxListActExportBtn);
        tableLayout = (TableLayout)findViewById(R.id.boxListActTableLayout);
        dbm = new DBManager(this);
        boxList = dbm.queryBoxGroupByType();

        SharedPreferences sharedPreferences = this.getSharedPreferences("BOXMANAGE", MODE_PRIVATE);

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"My Lock");


        titleText.setText(sharedPreferences.getString("PersonName", null)+"箱柜总览");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxlistActivity.this.finish();
                overridePendingTransition(R.anim.choose_act_return, R.anim.boxlist_act_out);
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
                    intent.putExtra("title",titleText.getText().toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
                String path = ExcelUtil.boxExport(Environment.getExternalStorageDirectory().toString()+"/Records", "箱柜列表_"+ Util.getDataFormat().format(new Date(System.currentTimeMillis()))+".xls", list);
                exportCompleteDialog(path);
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


    protected  void exportCompleteDialog(final String path){

        final AlertDialog dialog = new AlertDialog.Builder(BoxlistActivity.this).create();
        LayoutInflater inflater = LayoutInflater.from(BoxlistActivity.this);
        View view = inflater.inflate(R.layout.compent_for_dialog_export, null);
        dialog.setView(view);

        final Button cancelBtn = (Button)view.findViewById(R.id.alertDialogConcelBtn);
        final Button confirmBtn = (Button)view.findViewById(R.id.alertDialogConfirmBtn);
        final TextView textView = (TextView)view.findViewById(R.id.alertDialogText);

        textView.setText("文件成功导出，路径为\n \"" + path + "\" ");
        cancelBtn.setBackgroundResource(R.drawable.alert_dialog_down_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取wifi服务
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                //判断wifi是否开启
                if (!wifiManager.isWifiEnabled()) {
                    textView.setText("请打开WIFI");
                } else {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    int ipAddress = wifiInfo.getIpAddress();
                    String ip = intToIp(ipAddress);
                    if (ip.equals("0.0.0.0")) {
                        textView.setText("请链接上无线WIFI");
                    } else {
                        cancelBtn.setClickable(false);
                        cancelBtn.setVisibility(View.INVISIBLE);
                        confirmBtn.setBackgroundResource(R.drawable.alert_dialog_confirm_btn_2);

                        //屏幕常亮
                        wakeLock.acquire();

                        DownloadServer.getDownloadSever(ip);
                        textView.setText("请打开同局域网内的电脑浏览器，并在地址栏输入 \"" + ip + ":8080\"");
                    }
                }

            }
        });

        confirmBtn.setBackgroundResource(R.drawable.alert_dialog_complete_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wakeLock.isHeld())
                    wakeLock.release();
                dialog.dismiss();
                BoxlistActivity.this.finish();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(758, 374);
    }






    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

}
