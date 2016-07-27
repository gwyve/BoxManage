package com.ve.boxmanage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.Box;
import database.DBManager;

public class BoxContentActivity extends AppCompatActivity {

    Button backBtn;
    TextView textView;
    ListView listView;
    DBManager dbm;
    int boxid;
    List<Box>boxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_content);

        backBtn = (Button)findViewById(R.id.boxContentActBackBtn);
        textView = (TextView)findViewById(R.id.boxContentActTextView);
        listView = (ListView)findViewById(R.id.boxContentActListView);

        dbm = new DBManager(this);
        boxid = getIntent().getIntExtra("boxid",-1);
        boxes = dbm.queryBoxByBox("" + boxid);
        Log.e("111",boxes.toString());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxContentActivity.this.finish();
            }
        });

        textView.setText(boxid +"号");

        listView.setAdapter(new MyAdapter(this,getData(boxes)));
    }

    private List<String> getData(List<Box> boxList){
        List<String > dataList = new LinkedList<String>();
        for (int i = 0; i<boxList.size();i++){
            Box box = boxList.get(i);
            for (int j =0; j< box.getNumber();j++){
                String title = "【" + box.getVendor() +"】 【" + box.getModel() +"】 【" + box.getType() +"】 ";
                if ( !box.getMemo().equals("") && box.getMemo()!=null )
                    title += "【"+box.getMemo()+"】";
                dataList.add(title);
            }
        }
        return dataList;
    }



    private class MyAdapter extends BaseAdapter{
        private List<String> data;
        private LayoutInflater layoutInflater;
        private Context context;


        public MyAdapter(Context context,List<String> data){
            this.context = context;
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public final  class  Component{
            public TextView title;
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
            Component component = null;
            if (convertView == null){
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.adapter_box_content,null);
                component.title = (TextView)convertView.findViewById(R.id.adapterBoxContentActListViewTextView);
                convertView.setTag(component);
            }else {
                component = (Component)convertView.getTag();
            }
            component.title.setText(data.get(position));

            return convertView;
        }


    }
}
