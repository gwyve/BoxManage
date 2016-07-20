package database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import bean.Goods;
import bean.Person;

/**
 * Created by Administrator on 2016/7/19.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public Person addPerson(Person person){
        ContentValues values = new ContentValues();
        values.put("id",person.getId());
        values.put("name",person.getName());
        values.put("password",person.getPassword());
        long _id = db.insert("person",null,values);
        person.set_id(_id);
        db.close();
        return person;
    }

    public int deletePerson(Person person){
        return db.delete("person", "id=?", new String[]{String.valueOf(person.get_id())});
    }

    public List<Person> queryPerson(){
        LinkedList<Person> persons = new LinkedList<Person>();
        Cursor c = db.rawQuery("SELECT * FROM person",null);
        while (c.moveToNext()){
            Person person = new Person((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("id")),c.getString(c.getColumnIndex("name")),c.getString(c.getColumnIndex("password")));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    public List<Person> queryPerson(long _id){
        LinkedList<Person> persons = new LinkedList<Person>();
        Cursor c = db.rawQuery("SELECT * FROM person where _id = ?", new String[]{Long.toString(_id)});
        while (c.moveToNext()) {
            Person person = new Person((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("id")),c.getString(c.getColumnIndex("name")),c.getString(c.getColumnIndex("password")));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    public boolean loginPerson(String id,String password) {
        Cursor c = db.query("person", new String[]{"password"}, "id=?", new String[]{id}, null, null, null);
        while (c.moveToNext()){
            if (password.equals(c.getString(c.getColumnIndex("password"))))
                return true;
        }
        c.close();
        return false;
    }

    public Goods addGoods(Goods goods){
        ContentValues values = new ContentValues();
        values.put("id", goods.getId());
        values.put("vendor",goods.getVendor());
        values.put("model",goods.getModel());
        values.put("type", goods.getType());
        values.put("memo", goods.getMemo());
        long _id = db.insert("goods",null,values);
        goods.set_id(_id);
        db.close();
        return goods;
    }

    public int deleteGoods(Goods goods){
        return db.delete("goods", "id=?", new String[]{String.valueOf(goods.get_id())});
    }

    public List<Goods> queryGoods(){
        LinkedList<Goods> goodses = new LinkedList<Goods>();
        Cursor c = db.rawQuery("SELECT * FROM goods",null);
        while (c.moveToNext()){
            Goods goods = new Goods((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")));
            goodses.add(goods);
        }
        c.close();
        return goodses;
    }





}
