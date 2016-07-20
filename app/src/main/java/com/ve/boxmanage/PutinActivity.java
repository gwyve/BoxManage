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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bean.Goods;
import database.DBManager;

public class PutinActivity extends AppCompatActivity {

    private Button goodsAddBtn;
    private ListView listView;
    List<Goods> dbList;

    DBManager dbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putin);

        goodsAddBtn = (Button)findViewById(R.id.putinActGoodsAddButton);
        listView = (ListView)findViewById(R.id.putinActListView);
        dbm = new DBManager(this);
        dbList = dbm.queryGoods();
        List<Map<String,Object>> list = getData();


        listView.setAdapter(new MyAdaptr(this, getData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PutinActivity.this,BoxChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods",dbList.get((int)id));
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

        goodsAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PutinActivity.this, GoodsAddActivity.class);
                startActivity(intent);
                PutinActivity.this.finish();
            }
        });
    }

    public List<Map<String,Object>> getData(){
        List<Map<String ,Object>>  list= new LinkedList<Map<String,Object>>();
        for (int i = 0;i<dbList.size();i++){
            Map<String ,Object> map = new HashMap<String, Object>();
            map.put("title",dbList.get(i).toString());
            list.add(map);
        }
        return list;
    }





    public class MyAdaptr extends BaseAdapter{

        private List<Map<String,Object>> data;
        private LayoutInflater layoutInflater;
        private Context context;

        public MyAdaptr(Context context,List<Map<String,Object>> data){
            this.context = context;
            this.data = data;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public final class Component{
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
                convertView = layoutInflater.inflate(R.layout.adapter_putin,null);
                component.title = (TextView)convertView.findViewById(R.id.adapterPutinTextView);
                convertView.setTag(component);
            }else {
                component = (Component)convertView.getTag();
            }
            component.title.setText((String)data.get(position).get("title"));
            return convertView;
        }
    }
}
