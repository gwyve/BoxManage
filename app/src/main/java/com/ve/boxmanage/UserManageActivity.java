package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import Compent.MyAlertDialog;
import Compent.MyAlertDialogForUserAdd;
import Compent.UserButton;
import bean.Person;
import database.DBManager;

public class UserManageActivity extends AppCompatActivity {

    Button btn1;
    TableLayout tableLayout;
    DBManager dbm;
    List<Person> persons;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        btn1 = (Button)findViewById(R.id.userManageActBtn1);
        dbm = new DBManager(this);
        tableLayout = (TableLayout)findViewById(R.id.userManageActTableLayout);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManageActivity.this.finish();
            }
        });

        fresh();
    }


    private void fresh(){
        tableLayout.removeAllViews();

        persons = dbm.queryPerson();

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


    protected void userAddDialog(){

        final AlertDialog dialog = new AlertDialog.Builder(UserManageActivity.this).create();
        LayoutInflater inflater = LayoutInflater.from(UserManageActivity.this);
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

        final MyAlertDialog deleteDialog = new MyAlertDialog(UserManageActivity.this);
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
