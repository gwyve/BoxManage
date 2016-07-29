package Compent;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ve.boxmanage.R;

import com.ve.boxmanage.BoxlistActivity.ButtonData;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ViewForBoxlistAct extends RelativeLayout {

    private TextView boxText;
    private TextView contentText;


    public ViewForBoxlistAct(Context context, ButtonData buttonData) {
        super(context);
        View view = View.inflate(context, R.layout.compent_for_boxlist_act,this);
        boxText = (TextView) view.findViewById(R.id.boxlistActBoxidText);
        contentText = (TextView) view.findViewById(R.id.boxlistActContentText);

        boxText.setText(String.valueOf(buttonData.getBoxid()));
        contentText.setText(buttonData.getTitle());
    }

}
