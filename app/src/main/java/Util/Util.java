package Util;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/7/23.
 */
public class Util {
    public static SimpleDateFormat getDataFormat(){
        return new SimpleDateFormat("yyyy-MM-dd_HH;mm:ss");
    }
}
