package com.ve.boxmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import bean.Person;
import database.DBManager;

public class UserManageActivity extends AppCompatActivity {

    TextView textView ;

    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        textView = (TextView)findViewById(R.id.userManageActShowTextView);
        dbm = new DBManager(this);


        List<Person> persons = dbm.queryPerson();
        textView.setText(persons.toString());
    }
}
