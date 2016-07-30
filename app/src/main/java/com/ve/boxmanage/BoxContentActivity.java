package com.ve.boxmanage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    TextView titleText;
    Button backBtn;
    TextView textView;
    ListView listView;
    DBManager dbm;
    int boxid;
    List<Box>boxes;
    List<Box> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_content);

        titleText = (TextView)findViewById(R.id.boxContentActLeftTopTitleTextView);
        backBtn = (Button)findViewById(R.id.boxContentActBackBtn);
        textView = (TextView)findViewById(R.id.boxContentActTextView);
        listView = (ListView)findViewById(R.id.boxContentActListView);

        titleText.setText(getIntent().getStringExtra("title") +"  >  物品清单");

        dbm = new DBManager(this);
        boxid = getIntent().getIntExtra("boxid", -1);
        boxes = dbm.queryBoxByBox("" + boxid);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoxContentActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        if (boxid>9){
            textView.setText(String.valueOf(boxid));
        }else {
            textView.setText("  "+boxid);
        }
        dataList = getData(boxes);
        listView.setAdapter(new MyAdapter(this,dataList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BoxContentActivity.this, GoodsShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("box", dataList.get(position));
                intent.putExtras(bundle);
                intent.putExtra("title",titleText.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        ActivityManagerApplication.addDestoryActivity(BoxContentActivity.this,"BoxContentAct");
    }

    private List<Box> getData(List<Box> boxList){
        List<Box > dataList = new LinkedList<Box>();
        for (int i = 0; i<boxList.size();i++){
            Box box = boxList.get(i);
            for (int j =0; j< box.getNumber();j++){
                dataList.add(box);
            }
        }
        return dataList;
    }



    private class MyAdapter extends BaseAdapter{
        private List<Box> data;
        private LayoutInflater layoutInflater;
        private Context context;


        public MyAdapter(Context context,List<Box> data){
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
            Box box = data.get(position);
            String title = "【" + box.getVendor() +"】 【" + box.getModel() +"】 【" + box.getType() +"】 ";
            if ( !box.getMemo().equals("") && box.getMemo()!=null )
                title += "【"+box.getMemo()+"】";
            component.title.setText(title);

            return convertView;
        }


    }
}
