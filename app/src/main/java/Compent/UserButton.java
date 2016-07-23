package Compent;

import android.content.Context;
import android.widget.Button;

import bean.Person;

/**
 * Created by Administrator on 2016/7/23.
 */
public class UserButton extends Button{

    public Person person;
    public int index;
    public UserButton(Context context) {
        super(context);
    }
    public UserButton(Context context,Person person){
        super(context);
        this.person = person;
        this.setText(person.getName());
    }


}
