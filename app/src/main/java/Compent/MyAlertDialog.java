package Compent;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ve.boxmanage.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/7/29.
 */
public class MyAlertDialog  {

    int layoutRes;
    Context context;
    AlertDialog dialog;
    private Button cancelBtn;
    private Button confirmBtn;
    private LinearLayout titleLayout;


    public MyAlertDialog(Context context) {

        this.context = context;
        layoutRes = R.layout.compent_for_alert_dialog;

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(layoutRes);

        cancelBtn = (Button) window.findViewById(R.id.alertDialogConcelBtn);
        confirmBtn = (Button) window.findViewById(R.id.alertDialogConfirmBtn);
        titleLayout = (LinearLayout) window.findViewById(R.id.alertDialogLayout);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
    }

    public void dismiss(){
        dialog.dismiss();
    }

    //设置成删除dialog
    public void setDeleteDialog(String personName, View.OnClickListener listener){
        TextView textView = new TextView(context);
        textView.setText("确定删除 "+personName+" ?");
        textView.setTextSize(32);
        textView.setTextColor(context.getResources().getColor(R.color.blackColor));
        titleLayout.addView(textView);
        confirmBtn.setBackgroundResource(R.drawable.alert_dialog_delete_btn);
        confirmBtn.setOnClickListener(listener);
    }

    //设置成取物确定dialog
    public Button setPutinDialog(String msg){
        TextView textView = new TextView(context);
        textView.setText(msg);
        textView.setTextSize(32);
        textView.setTextColor(context.getResources().getColor(R.color.blackColor));
        titleLayout.addView(textView);

        return confirmBtn;
    }







}
