package Compent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.ve.boxmanage.BoxlistActivity;
import com.ve.boxmanage.R;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MyAlertDialogForUserAdd extends AlertDialog{
    private Context context;
    Button cancelBtn;
    Button confirmBtn;
    EditText editText;


    public MyAlertDialogForUserAdd(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        init();
    }

    public EditText init(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.compent_for_alert_dialog, null);
        setContentView(view);

        MyAlertDialogForUserAdd.this.show();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
                params.height = 200 ;
        MyAlertDialogForUserAdd.this.getWindow().setAttributes(params);
//        cancelBtn = (Button) findViewById(R.id.alertDialogUserAddConcelBtn);
//        confirmBtn = (Button)findViewById(R.id.alertDialogUserAddConfirmBtn);
//        editText = (EditText) findViewById(R.id.alertDialogUserAddEditText);
//
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyAlertDialogForUserAdd.this.dismiss();
//            }
//        });
//
        return editText;
    }


}
