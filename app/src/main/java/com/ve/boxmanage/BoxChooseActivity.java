package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Compent.MyAlertDialog;
import Util.Util;
import bean.Box;
import bean.Goods;
import bean.Item;
import database.DBManager;

public class BoxChooseActivity extends AppCompatActivity {

    Button backBtn;
    ListView listView;
    Item item;
    SharedPreferences sharedPreferences;
    DBManager dbm;
    List<Box> boxes;
    String type;
    //adapter所用到的list
    List<Map<String,Object>> dataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_choose);
        item = (Item)getIntent().getSerializableExtra("item");
        type = item.getType();

        dbm = new DBManager(this);
        boxes = dbm.queryBox();

        sharedPreferences = this.getSharedPreferences("BOXMANAGE", MODE_PRIVATE);

        backBtn = (Button) findViewById(R.id.boxChooseActBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxChooseActivity.this.finish();
            }
        });

        listView = (ListView)findViewById(R.id.boxChooseActListView);
        dataList = getData(boxes);
        listView.setAdapter(new MyAdapter(this,dataList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boxChooseDialog((Integer) dataList.get(position).get("box"));
            }
        });

    }


    private List<Map<String,Object>> getData( List<Box> boxes){
        List<Map<String,Object>> dataList = new LinkedList<Map<String,Object>>();
        List<MyAdapterData> tmpList = new ArrayList<MyAdapterData>();
        for (int i = 1;i <= getResources().getInteger(R.integer.box_total_number);i++)
            tmpList.add(new MyAdapterData(i));
        for (int i =0; i <boxes.size(); i++){
            Box box = boxes.get(i);
            MyAdapterData data = tmpList.get(Integer.parseInt(box.getBox())-1);
            if (data.items.containsKey(box.getType())){
                int tmp = data.items.get(box.getType());
                tmp += box.getNumber();
                data.items.put(box.getType(),new Integer(tmp));
            }else {
                data.items.put(box.getType(),new Integer(box.getNumber()));
            }
        }
        for (int i = 0; i < getResources().getInteger(R.integer.box_total_number);i++){
            if (tmpList.get(i).items.size() == 1 && tmpList.get(i).items.containsKey(type)){
                tmpList.get(i).level = 1;
            }else if (tmpList.get(i).items.size() > 0 &&  !tmpList.get(i).items.containsKey(type)){
                tmpList.get(i).level = 3;
            }
        }
        Collections.sort(tmpList);
        for (int i = 0; i < getResources().getInteger(R.integer.box_total_number);i++){
            String title = new String();
            int j = 0;
            for (Map.Entry<String,Integer> entry : tmpList.get(i).items.entrySet()){
                if ( j < 3){
                    j++;
                    title += entry.getKey()+"×"+entry.getValue()+"   ";
                }else {
                    title += "……";
                    j = 0;
                    break;
                }
            }
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("box",tmpList.get(i).box);
            map.put("title",title);
            map.put("level",tmpList.get(i).level);
            dataList.add(map);
        }
        return dataList;
    }




    public class MyAdapterData implements Comparable<MyAdapterData>{
        int box;
        int level = 2;
        Map<String,Integer> items;
        public MyAdapterData(int boxid ){
            box = boxid;
            items = new HashMap<String,Integer>();
        }

        public String toString(){
            return "box:"+box +"  level:"+level +"  items:"+items;
        }

        @Override
        public int compareTo(MyAdapterData another) {
            if (this.level < another.level){
                return -1;
            }else if (this.level > another.level){
                return 1;
            }else {
                if (this.items.size() < another.items.size()){
                    return -1;
                }else if (this.items.size() > another.items.size()){
                    return 1;
                }else if (this.items.size() == 1 && this.items.containsKey(type)){
                    if (this.items.get(type).intValue() < another.items.get(type).intValue()){
                        return -1;
                    }else if (this.items.get(type).intValue() > another.items.get(type).intValue()){
                        return 1;
                    }else {
                        return 0;
                    }
                }else{
                    return 0;
                }
            }
        }
    }


    private class MyAdapter extends BaseAdapter{

        private List<Map<String,Object>> data;
        private LayoutInflater layoutInflater;
        private Context context;


        public MyAdapter(Context context,List<Map<String, Object>>data){
            this.context = context;
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public final  class  Component{
            public RelativeLayout linearLayout;
            public TextView title;
            public TextView boxText;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Map<String,Object> positionData = data.get(position);
            Component component = null;
            if (convertView == null){
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.adapter_box_choose_act,null);
                component.linearLayout = (RelativeLayout) convertView.findViewById(R.id.adapterBoxChooseActItem);
                component.boxText = (TextView)convertView.findViewById(R.id.adapterBoxChooseActBoxTextView);
                component.title = (TextView)convertView.findViewById(R.id.adapterBoxChooseActTextView);
                convertView.setTag(component);
            }else {
                component = (Component)convertView.getTag();
            }
            if ((Integer)positionData.get("box") >9){
                component.boxText.setText(data.get(position).get("box").toString());
            }else {
                component.boxText.setText("  "+data.get(position).get("box").toString());
            }
            component.title.setText((String) data.get(position).get("title"));
            switch((Integer)data.get(position).get("level")){
                case 1:
                    component.linearLayout.setBackgroundResource(R.drawable.box_choose_act__list_item_level1);
                    break;
                case 2:
                    component.linearLayout.setBackgroundResource(R.drawable.box_choose_act__list_item_level2);
                    break;
                case 3:
                    component.linearLayout.setBackgroundResource(R.drawable.box_choose_act__list_item_level3);
                    break;
            }
            return convertView;
        }
    }

    protected void boxChooseDialog(final int box){
        final int[] seconds = {5};
        final MyAlertDialog dialog = new MyAlertDialog(BoxChooseActivity.this);
        final Button confirmBtn = dialog.setPutinDialog("请将物品放入" + box + "号柜，然后确认。");
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setBox(box + "");
                item.setPerson_id(sharedPreferences.getLong("Person_id", -1) + "");
                item.setPersonName(sharedPreferences.getString("PersonName", null));
                item.setNumber(item.getNumber());
                item.setTime(Util.getDataFormat().format(new Date(System.currentTimeMillis())));

                if(dbm.putin(item) ){
                    dialog.dismiss();
                    ActivityManagerApplication.destroyActivity("putinAct");
                    BoxChooseActivity.this.finish();
                }
            }
        });
        confirmBtn.setText("                (" + seconds[0] + "s)");
        confirmBtn.setTextSize(24);
        confirmBtn.setTextColor(getResources().getColor(R.color.greenColor));
        confirmBtn.setClickable(false);
        confirmBtn.setBackgroundResource(R.drawable.alert_dialog_confirm_wait_btn);
        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                if (msg.what == 1){
                    confirmBtn.setText("  ");
                    confirmBtn.setBackgroundResource(R.drawable.alert_dialog_confirm_btn);
                    confirmBtn.setClickable(true);
                }else {
                    confirmBtn.setText("                ("+seconds[0]+"s)");
                }

            }
        };
        final Timer timer = new Timer(true);
        final TimerTask timerTask = new TimerTask() {
            public void run(){
                Message msg = new Message();
                if (seconds[0] == 1){
                    msg.what = seconds[0];
                    handler.sendMessage(msg);
                    timer.cancel();
                }else {
                    msg.what = seconds[0];
                    seconds[0]--;
                    handler.sendMessage(msg);
                }
            }
        };
        timer.schedule(timerTask,1000,1000);





//        AlertDialog.Builder builder = new AlertDialog.Builder(BoxChooseActivity.this);
//        builder.setMessage("请将物品放入" + box + "号柜，然后确认。");
//
//        builder.setPositiveButton("确认放入", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                item.setBox(box + "");
//                item.setPerson_id(sharedPreferences.getLong("Person_id", -1) + "");
//                item.setPersonName(sharedPreferences.getString("PersonName", null));
//                item.setNumber(item.getNumber());
//                item.setTime(Util.getDataFormat().format(new Date(System.currentTimeMillis())));
//
//                if(dbm.putin(item) ){
//                    dialog.dismiss();
//                    ActivityManagerApplication.destroyActivity("putinAct");
//                    BoxChooseActivity.this.finish();
//                }
//
//
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.show();
    }



}
