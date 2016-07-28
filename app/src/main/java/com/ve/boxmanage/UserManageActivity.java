package com.ve.boxmanage;

import android.app.AlertDialog;
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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import Compent.UserButton;
import bean.Person;
import database.DBManager;

public class UserManageActivity extends AppCompatActivity {

    Button btn1;
    TableLayout tableLayout;
    DBManager dbm;
    List<Person> persons;

    EditText addNameTextView;
    AlertDialog addDialog;


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
        int rowIndex;
        int colIndex;
        for(rowIndex = 0; rowIndex < persons.size()/5; rowIndex++){
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0,0,0,53);
            for (colIndex = 0; colIndex < 5 ;colIndex++){
                final UserButton button = new UserButton(this,persons.get(rowIndex*5+colIndex));
                button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
                button.setWidth(209);
                button.setHeight(209);
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
        }
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0,0,0,53);
        for (int index = rowIndex*5;index<persons.size();index++){
            final UserButton button = new UserButton(this,persons.get(index));
            button.setBackgroundResource(R.drawable.user_manage_act_user_btn);
            button.setWidth(209);
            button.setHeight(209);
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
        tableRow = new TableRow(this);
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

    }


    protected void userAddDialog(){
        addNameTextView = new EditText(this);
        final AlertDialog dialog ;
        InputFilter[] filters = {new InputFilter.LengthFilter(5)};
        addNameTextView.setFilters(filters);

        addNameTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    InputMethodManager inputMethodManager = (InputMethodManager) UserManageActivity.this.getSystemService(UserManageActivity.this.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(addNameTextView.getWindowToken(), 0);
                    if (!addNameTextView.getText().toString().equals("") && !addNameTextView.getText().toString().substring(0, addNameTextView.getText().length() - 1).replace(" ","").equals("")) {
                        dbm.addPerson(addNameTextView.getText().toString().substring(0, addNameTextView.getText().length() - 1));
                        fresh();
                    }
                    new Thread(addDialogCloseRunnable).start();
                }
                return false;
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(UserManageActivity.this);
        builder.setTitle("添加用户");
        builder.setView(addNameTextView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!addNameTextView.getText().toString().equals("") && !addNameTextView.getText().toString().replace(" ","").equals("")) {
                    dbm.addPerson(addNameTextView.getText().toString());
                    fresh();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        addDialog = builder.create();
        addDialog.setCancelable(false);
        addDialog.show();
    }

    protected  void userDeleteDialog(final Person person){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserManageActivity.this);
        builder.setMessage("确定要删除 "+person.getName());

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbm.deletePerson(person);
                dialog.dismiss();
                fresh();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();
    }


    //关闭dialog另开进程
    Runnable addDialogCloseRunnable = new Runnable() {
        @Override
        public void run() {
            addDialog.dismiss();
        }
    };


}
