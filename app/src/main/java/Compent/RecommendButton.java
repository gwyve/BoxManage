package Compent;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;

import com.ve.boxmanage.R;

/**
 * Created by Administrator on 2016/7/30.
 */
public class RecommendButton extends Button {
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public RecommendButton(Context context,String title,OnClickListener listener) {
        super(context);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setText(title);
        this.setOnClickListener(listener);
        this.setTextSize(24);
        this.setTextColor(getResources().getColor(R.color.blackColor));
        this.setAllCaps(false);

        this.setPadding(24,6,24,0);
    }
}
