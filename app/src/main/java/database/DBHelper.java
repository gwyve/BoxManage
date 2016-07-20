package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/19.
 */
public class DBHelper extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "boxmanage.db";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR, name VARCHAR, password VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS goods" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR, vendor VARCHAR," +
                "model VARCHAR, type VARCHAR, memo VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS item" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, time VARCHAR, goodsid VARCHAR, personid VARCHAR," +
                "box VARCHAR, action VARCHAR, memo VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
        db.execSQL("ALTER TABLE goods ADD COLUMN other STRING");
        db.execSQL("ALTER TABLE item ADD COLUMN other STRING");
    }
}
