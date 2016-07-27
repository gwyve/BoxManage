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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import bean.Box;
import database.DBManager;

public class SearchActivity extends AppCompatActivity {

    Button backBtn;
    EditText editText;
    Button searchBtn;
    ListView listView;

    DBManager dbm;
    List<Box> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backBtn = (Button) findViewById(R.id.searchActBackBtn);
        editText = (EditText) findViewById(R.id.searchActEditText);
        searchBtn = (Button) findViewById(R.id.searchActSearchBtn);
        listView = (ListView) findViewById(R.id.searchActListView);

        dbm = new DBManager(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new LinkedList<String>();

                StringTokenizer st = new StringTokenizer(editText.getText().toString());
                while (st.hasMoreElements()){
                    list.add(st.nextToken());
                }
                switch (list.size()){
                    case 0:
                        break;
                    case 1:
                        dataList = dbm.queryBoxByGoodsid(list.get(0));
                        break;
                    case 2:
                        dataList = dbm.queryBoxByGoodsid(list.get(0), list.get(1));
                        break;
                    case 3:
                        dataList = dbm.queryBoxByGoodsid(list.get(0), list.get(1), list.get(2));
                        break;
                    case 4:
                        dataList = dbm.queryBoxByGoodsid(list.get(0),list.get(1),list.get(2),list.get(3));
                        break;
                    default:
                        break;
                }
                listView.setAdapter(new MyAdapter(SearchActivity.this,dataList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SearchActivity.this, GoodsShowActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("box", dataList.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });

        ActivityManagerApplication.addDestoryActivity(SearchActivity.this,"SearchAct");
    }



    private class MyAdapter extends BaseAdapter{
        private List<Box> data;
        private LayoutInflater layoutInflater;
        private Context context;


        public MyAdapter(Context context,List<Box>data){
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
                convertView = layoutInflater.inflate(R.layout.adapter_search_act,null);
                component.title = (TextView)convertView.findViewById(R.id.adapterSearchActTextView);
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
