package com.ve.boxmanage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import database.DBManager;

public class LoginActivity extends AppCompatActivity {


    EditText idText;
    EditText psdText;
    RadioButton rememberBtn;
    RadioButton autoBtn;
    Button confirmBtn;
    Button resetBtn;

    SharedPreferences sharedPreferences;
    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        idText = (EditText)findViewById(R.id.loginActIDEditText);
        psdText = (EditText)findViewById(R.id.loginActPasswordEditText);
        rememberBtn = (RadioButton)findViewById(R.id.loginActRemRadioButton);
        autoBtn = (RadioButton)findViewById(R.id.loginActAutoRadioButton);
        confirmBtn = (Button)findViewById(R.id.loginActConfirmButton);
        resetBtn = (Button)findViewById(R.id.loginActResetButton);

        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_WORLD_WRITEABLE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        dbm = new DBManager(this);


        if (sharedPreferences.getBoolean("AUTO",false)){
            if (dbm.loginPerson(sharedPreferences.getString("ID",""),sharedPreferences.getString("PASSWORD", ""))){
                if (sharedPreferences.getString("ID","").equals(R.string.super_id)){
                    Intent intent = new Intent(LoginActivity.this,UserManageActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else {
                    Intent intent = new Intent(LoginActivity.this,ChooseActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        }

        if (sharedPreferences.getBoolean("REMEMBER",false)){
            idText.setText(sharedPreferences.getString("ID",""));
            psdText.setText(sharedPreferences.getString("PASSWORD",""));
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbm.loginPerson(idText.getText().toString(),psdText.getText().toString())){
                    if (rememberBtn.isChecked()){
                        editor.putBoolean("REMEMBER",true);
                        editor.putString("ID",idText.getText().toString());
                        editor.putString("PASSWORD",psdText.getText().toString());
                        editor.commit();
                    }
                    if(autoBtn.isChecked()){
                        editor.putBoolean("AUTO",true);
                        editor.commit();
                    }
                    if (idText.getText().toString().equals(R.string.super_id)){
                        Intent intent = new Intent(LoginActivity.this,UserManageActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }else{
                        Intent intent = new Intent(LoginActivity.this,ChooseActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                }else{
                    dialog("密码错误");
                    psdText.setText("");
                }
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idText.setText("");
                psdText.setText("");
            }
        });


    }

    protected void dialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
