package Util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DownloadServer {

    private MiniServer miniServer = null;
    private Thread miniServerThread = null;

    private static DownloadServer instance;
    public static synchronized DownloadServer getDownloadSever(String ip){
        if (instance == null){
            instance = new DownloadServer(ip);
        }
        return instance;
    }

    private DownloadServer(String ip){
        if (miniServerThread==null){
            miniServer = new MiniServer(new File(Environment.getExternalStorageDirectory().toString()+"/Records"), ip);
            miniServerThread = new Thread(miniServer);
            miniServerThread.setDaemon(true);
            miniServerThread.start();
        }else{
            if(!miniServerThread.isAlive()){
                miniServerThread.start();
            }
        }
    }
}
