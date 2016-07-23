package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    TextView textView ;
    Button btn1;
    TableLayout tableLayout;
    DBManager dbm;
    List<Person> persons;

    EditText addNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        textView = (TextView)findViewById(R.id.userManageActView);
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
            for (colIndex = 0; colIndex < 5 ;colIndex++){
                final UserButton button = new UserButton(this,persons.get(rowIndex*5+colIndex));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userDeleteDialog(button.person);
                    }
                });
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
        TableRow tableRow = new TableRow(this);
        for (int index = rowIndex*5;index<persons.size();index++){
            final UserButton button = new UserButton(this,persons.get(index));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userDeleteDialog(button.person);
                }
            });
            tableRow.addView(button);
        }
        tableLayout.addView(tableRow);
        tableRow = new TableRow(this);
        Button button = new Button(this);
        button.setText("添加用户");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UserManageActivity.this);
        builder.setTitle("添加用户");
        builder.setView(addNameTextView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbm.addPerson(addNameTextView.getText().toString());
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

    protected  void userDeleteDialog(final Person person){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserManageActivity.this);
        builder.setMessage("确定要删除"+person.getName());
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

}
