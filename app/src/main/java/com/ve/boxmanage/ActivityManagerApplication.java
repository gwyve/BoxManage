package com.ve.boxmanage;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/25.
 */
public class ActivityManagerApplication extends Application {
    private static Map<String ,Activity> destoryMap = new HashMap<>();
    private ActivityManagerApplication(){

    }

    public   static void addDestoryActivity(Activity activity,String activityName){
        destoryMap.put(activityName,activity);
    }

    public static void destroyActivity(String activityName){
        Set<String> keySet = destoryMap.keySet();
        for (String key:keySet){
            destoryMap.get(key).finish();
        }
    }
}
