package Compent;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ve.boxmanage.R;

import bean.Item;


/**
 * Created by Administrator on 2016/7/28.
 */
public class ItemForRecordAct extends LinearLayout {

    private TextView dataText;
    private TextView timeText;
    private TextView vendorText;
    private TextView typeText;
    private TextView modelText;
    private TextView personText;
    private TextView boxText;
    private TextView explainText;

    public ItemForRecordAct(Context context, Item item) {
        super(context);
        View view = View.inflate(context, R.layout.compent_for_record_act,this);
        dataText = (TextView)view.findViewById(R.id.recordActDateText);
        timeText = (TextView)view.findViewById(R.id.recordActTimeText);
        vendorText = (TextView)view.findViewById(R.id.recordActVendorText);
        typeText = (TextView)view.findViewById(R.id.recordActTypeText);
        modelText = (TextView)view.findViewById(R.id.recordActModelText);
        personText = (TextView)view.findViewById(R.id.recordActPersonText);
        boxText = (TextView)view.findViewById(R.id.recordActBoxidText);
        explainText = (TextView)view.findViewById(R.id.recordActExplainText);

        String[] times = item.getTime().split("_");
        dataText.setText(times[0]);
        timeText.setText(times[1]);
        if (item.getType().length()>4){
            vendorText.setText(item.getVendor().substring(0,3)+"...");
        }else {
            vendorText.setText(item.getVendor());
        }
        if (item.getType().length()>4){
            typeText.setText(item.getType().substring(0,3)+"...");
        }else {
            typeText.setText(item.getType());
        }
        if (item.getModel().length()>9){
            modelText.setText(item.getModel().substring(0,8)+"...");
        }else {
            modelText.setText(item.getModel());
        }
        if (item.getPersonName().length()>4){
            personText.setText(item.getPersonName().substring(0,3)+"...");
        }else {
            personText.setText(item.getPersonName());
        }
        boxText.setText(item.getBox());
        if (item.getAction().equals("putin")){
            explainText.setText("放物");
        }else {
            if (item.getExplain().length()>18){
                explainText.setText(item.getExplain().substring(0,17)+"...");
            }else{
                explainText.setText(item.getExplain());
            }
        }

    }
}
