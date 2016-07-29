package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import Compent.MyAlertDialog;
import Compent.UserButton;
import bean.Person;
import database.DBManager;

public class UserActivity extends AppCompatActivity {


    Button btn;
    TableLayout tableLayout;
    DBManager dbm;
    List<Person> persons;

    //选择用户还是管理用户的flag,true为选择用户,false为管理用户
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btn = (Button)findViewById(R.id.userChooseActManagetBtn);
        tableLayout = (TableLayout)findViewById(R.id.userChooseActTableLayout);
        dbm = new DBManager(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    flag = false;
                    btn.setBackgroundResource(R.drawable.user_manage_act_back_btn);
                }else {
                    flag = true;
                    btn.setBackgroundResource(R.drawable.user_choose_act_managet_btn);
                }
                fresh();
            }
        });

        fresh();

    }


    private void fresh(){
        persons = dbm.queryPerson();
        if (flag){
            //此为userchoose阶段
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


        }else{
            //此为usermanage阶段
            tableLayout.removeAllViews();

            if (persons.size()<10){
                if (persons.size()<5){
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0,0,0,53);
                    for (int i=0;i<persons.size();i++){
                        final UserButton button = new UserButton(this,persons.get(i));
                        button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userDeleteDialog(button.person);
                            }
                        });
                        tableRow.addView(button);
                        TextView textView = new TextView(this);
                        textView.setWidth(22);
                        tableRow.addView(textView);
                    }
                    Button button = new Button(this);
                    button.setPadding(0,8,7,15);
                    button.setBackgroundResource(R.drawable.user_manage_act_user_add_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userAddDialog();
                        }
                    });
                    tableRow.addView(button);
                    tableLayout.addView(tableRow);

                }else {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setPadding(0,0,0,53);
                    for (int i=0;i<5;i++){
                        final UserButton button = new UserButton(this,persons.get(i));
                        button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userDeleteDialog(button.person);
                            }
                        });
                        tableRow.addView(button);
                        TextView textView = new TextView(this);
                        textView.setWidth(22);
                        tableRow.addView(textView);
                    }
                    tableLayout.addView(tableRow);
                    TableRow tableRow2 = new TableRow(this);
                    for (int i=5;i<persons.size();i++){
                        final UserButton button = new UserButton(this,persons.get(i));
                        button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userDeleteDialog(button.person);
                            }
                        });
                        tableRow2.addView(button);
                        TextView textView = new TextView(this);
                        textView.setWidth(22);
                        tableRow2.addView(textView);
                    }
                    Button button = new Button(this);
                    button.setPadding(0,8,7,15);
                    button.setBackgroundResource(R.drawable.user_manage_act_user_add_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userAddDialog();
                        }
                    });
                    tableRow2.addView(button);
                    tableLayout.addView(tableRow2);
                }
            }else if (persons.size() == 10){
                TableRow tableRow = new TableRow(this);
                tableRow.setPadding(0,0,0,53);
                for (int i=0;i<5;i++){
                    final UserButton button = new UserButton(this,persons.get(i));
                    button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userDeleteDialog(button.person);
                        }
                    });
                    tableRow.addView(button);
                    TextView textView = new TextView(this);
                    textView.setWidth(22);
                    tableRow.addView(textView);
                }
                tableLayout.addView(tableRow);
                TableRow tableRow2 = new TableRow(this);
                for (int i=5;i<persons.size();i++){
                    final UserButton button = new UserButton(this,persons.get(i));
                    button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userDeleteDialog(button.person);
                        }
                    });
                    tableRow2.addView(button);
                    TextView textView = new TextView(this);
                    textView.setWidth(22);
                    tableRow2.addView(textView);
                }
                tableLayout.addView(tableRow2);
            }

        }





    }


    private class UserDeleteListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            userDeleteDialog(((UserButton)v).person);
        }
    }



    protected class UserButtonListener implements View.OnClickListener{
        Person person;

        UserButtonListener(Person person){
            this.person = person;
        }
        @Override
        public void onClick(View v) {
            SharedPreferences sp = UserActivity.this.getSharedPreferences("BOXMANAGE",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong("Person_id", person.get_id());
            editor.putString("PersonName", person.getName());
            editor.commit();
            Intent intent = new Intent(UserActivity.this,ChooseActivity.class);
            startActivity(intent);
        }
    }




    protected void userAddDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(UserActivity.this).create();
        LayoutInflater inflater = LayoutInflater.from(UserActivity.this);
        View view = inflater.inflate(R.layout.compent_for_alert_dialog_user_add, null);
        dialog.setView(view);

        view.findViewById(R.id.alertDialogUserAddConcelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText addUserEditText = (EditText)view.findViewById(R.id.alertDialogUserAddEditText);
        view.findViewById(R.id.alertDialogUserAddConfirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addUserEditText.getText().toString().equals("") && !addUserEditText.getText().toString().replace(" ","").equals("")) {
                    dbm.addPerson(addUserEditText.getText().toString());
                    fresh();
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setLayout(758, 374);

    }

    protected  void userDeleteDialog(final Person person){

        final MyAlertDialog deleteDialog = new MyAlertDialog(UserActivity.this);
        deleteDialog.setDeleteDialog(person.getName(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbm.deletePerson(person);
                deleteDialog.dismiss();
                fresh();
            }
        });
    }
}
