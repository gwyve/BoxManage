package com.ve.boxmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import Compent.UserButton;
import bean.Person;
import database.DBManager;

public class UserChooseActvity extends AppCompatActivity {

    Button btn;
    TextView textView ;
    TableLayout tableLayout;
    DBManager dbm;

    List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choose_actvity);
        btn = (Button)findViewById(R.id.userChooseActManagetBtn);
        textView = (TextView)findViewById(R.id.userChooseActTextView);
        tableLayout = (TableLayout)findViewById(R.id.userChooseActTableLayout);
        dbm = new DBManager(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserChooseActvity.this, UserManageActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        fresh();
    }


    protected void fresh(){
        tableLayout.removeAllViews();
        persons = dbm.queryPerson();
        int rowIndex;
        int colIndex;
        for(rowIndex = 0; rowIndex <persons.size() /5; rowIndex++){
            TableRow tableRow = new TableRow(this);
            for (colIndex = 0; colIndex < 5 ;colIndex++){
                Button button = new UserButton(this,persons.get(rowIndex*5+colIndex));
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
        TableRow tableRow = new TableRow(this);
        for (int index = rowIndex*5;index<persons.size();index++){
            Button button = new UserButton(this,persons.get(index));
            tableRow.addView(button);
        }
        tableLayout.addView(tableRow);

    }
}
