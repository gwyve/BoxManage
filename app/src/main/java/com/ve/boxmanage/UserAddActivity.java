package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bean.Person;
import database.DBManager;

public class UserAddActivity extends AppCompatActivity {

    EditText idText;
    EditText nameText;
    EditText psdText;
    Button confirmBtn;
    Button resetBtn;
    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        idText = (EditText)findViewById(R.id.userAddActIdEditText);
        nameText = (EditText)findViewById(R.id.userAddActNameEditText);
        psdText = (EditText)findViewById(R.id.userAddActPasswordEditText);
        confirmBtn = (Button)findViewById(R.id.userAddActConfirmButton);
        resetBtn = (Button)findViewById(R.id.userAddActResetButton);
        dbm = new DBManager(this);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idText.setText("");
                nameText.setText("");
                psdText.setText("");
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(nameText.getText().toString());
                person = dbm.addPerson(person);
                if ((Long)person.get_id() !=null)
                    dialog("添加成功");
            }
        });

    }

    protected void dialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAddActivity.this);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UserAddActivity.this.finish();
            }
        });
        builder.create().show();
    }


}
