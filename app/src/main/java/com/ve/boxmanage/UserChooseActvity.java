package com.ve.boxmanage;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import Compent.UserButton;
import bean.Person;
import database.DBManager;

public class UserChooseActvity extends AppCompatActivity {

    Button btn;

    TableLayout tableLayout;
    DBManager dbm;

    List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choose_actvity);
        btn = (Button)findViewById(R.id.userChooseActManagetBtn);
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
            tableRow.setPadding(0,0,0,53);
            for (colIndex = 0; colIndex < 5 ;colIndex++){
                final UserButton button = new UserButton(this,persons.get(rowIndex*5+colIndex));
                button.setBackgroundResource(R.drawable.user_choose_act_user_btn);
                button.setWidth(194);
                button.setHeight(194);
                button.setOnClickListener(new UserButtonListener(button.person));
                tableRow.addView(button);
                TextView textView = new TextView(this);
                textView.setWidth(52);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        TableRow tableRow = new TableRow(this);
        for (int index = rowIndex*5;index<persons.size();index++){
            final UserButton button = new UserButton(this,persons.get(index));
            button.setWidth(194);
            button.setHeight(194);
            button.setBackgroundResource(R.drawable.user_choose_act_user_btn);
            button.setOnClickListener(new UserButtonListener(button.person));
            tableRow.addView(button);
            TextView textView = new TextView(this);
            textView.setWidth(52);
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);
    }

    protected class UserButtonListener implements View.OnClickListener{
        Person person;

        UserButtonListener(Person person){
               this.person = person;
        }
        @Override
        public void onClick(View v) {
            SharedPreferences sp = UserChooseActvity.this.getSharedPreferences("BOXMANAGE",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("Person_id", person.get_id());
            editor.putString("PersonName", person.getName());
            editor.commit();
            Intent intent = new Intent(UserChooseActvity.this,ChooseActivity.class);
            startActivity(intent);
        }
    }
}
