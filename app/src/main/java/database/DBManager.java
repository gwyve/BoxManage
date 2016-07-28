package database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import bean.Box;
import bean.Goods;
import bean.Item;
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

    public long addPerson(String name){
        ContentValues values = new ContentValues();
        values.put("name", name);
        return db.insert("person",null,values);
    }

    public Person addPerson(Person person){
        ContentValues values = new ContentValues();
//        values.put("id",person.getId());
        values.put("name",person.getName());
//        values.put("password", person.getPassword());
        long _id = db.insert("person",null,values);
        person.set_id(_id);
        db.close();
        return person;
    }

    public int deletePerson(Person person){
        return db.delete("person", "_id=?", new String[]{String.valueOf(person.get_id())});
    }

    public List<Person> queryPerson(){
        LinkedList<Person> persons = new LinkedList<Person>();
        Cursor c = db.rawQuery("SELECT * FROM person",null);
        while (c.moveToNext()){
            Person person = new Person((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("name")));
            persons.add(person);
//            Person person = new Person((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("id")),c.getString(c.getColumnIndex("name")),c.getString(c.getColumnIndex("password")));
//            Person person = new Per
//            persons.add(person);
        }
        c.close();
        return persons;
    }

    public List<Person> queryPerson(long _id){
        LinkedList<Person> persons = new LinkedList<Person>();
        Cursor c = db.rawQuery("SELECT * FROM person where _id = ?", new String[]{Long.toString(_id)});
        while (c.moveToNext()) {
//            Person person = new Person((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("id")),c.getString(c.getColumnIndex("name")),c.getString(c.getColumnIndex("password")));
//            persons.add(person);
            Person person = new Person((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("name")));
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




    public Item addItem(Item item){
        ContentValues values = new ContentValues();
        values.put("time",item.getTime());
        values.put("goodsid",item.getGoodsId());
        values.put("vendor",item.getVendor());
        values.put("model",item.getModel());
        values.put("type",item.getType());
        values.put("memo",item.getMemo());
        values.put("person_id",item.getPerson_id());
        values.put("personname",item.getPersonName());
        values.put("box",item.getBox());
        values.put("action",item.getAction());
        values.put("explain",item.getExplain());
        values.put("number",item.getNumber());
        long _id = db.insert("item", null, values);
        item.set_id(_id);
        return item;
    }

    public List<Item> queryItem(){
        List<Item> list = new LinkedList<Item>();
        Cursor c = db.rawQuery("SELECT * FROM item", null);
        while (c.moveToNext()){
            Item item = new Item((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("time")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getString(c.getColumnIndex("person_id")),c.getString(c.getColumnIndex("personname")),
                    c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("action")),c.getString(c.getColumnIndex("explain")),
                    c.getInt(c.getColumnIndex("number")));
            list.add(item);
        }
        c.close();
        return list;
    }

    public List<Item> queryItemByBox(String box){
        List<Item> list = new LinkedList<Item>();
        Cursor c = db.query("item", new String[]{"box"}, "id=?", new String[]{box}, null, null, null);
        while (c.moveToNext()){
            Item item = new Item((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("time")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getString(c.getColumnIndex("person_id")),c.getString(c.getColumnIndex("personname")),
                    c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("action")),c.getString(c.getColumnIndex("explain")),
                    c.getInt(c.getColumnIndex("number")));
            list.add(item);
        }
        c.close();
        return list;
    }

    public List<Item> queryItemGroupByBoxType(){
        List<Item> list = new LinkedList<Item>();
        Cursor c = db.rawQuery("SELECT box,type,SUM(number) FROM item GROUP BY box,type ORDER BY box", null);
        while (c.moveToNext()){
            Item item = new Item(c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("type")),c.getInt(c.getColumnIndex("SUM(number)")));
            list.add(item);
        }
        c.close();
        return list;
    }


    public Box addBox(Box box){
        ContentValues values = new ContentValues();
        values.put("box",box.getBox());
        values.put("goodsid",box.getGoodsId());
        values.put("vendor",box.getVendor());
        values.put("model",box.getModel());
        values.put("type",box.getType());
        values.put("memo",box.getMemo());
        values.put("number",box.getNumber());
        long _id = db.insert("box", null, values);
        box.set_id(_id);
        db.close();
        return box;
    }

    public  List<Box> queryBox(){
        List<Box> list = new ArrayList<Box>();
        Cursor c = db.rawQuery("SELECT * FROM box ORDER BY box ASC", null);
        while (c.moveToNext()){
            Box box = new Box((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("box")), c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")), c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getInt(c.getColumnIndex("number")));
            list.add(box);
        }
        c.close();
        return list;
    }

    public boolean putin(Item item){

        ContentValues itemValues = new ContentValues();
        ContentValues boxValues = new ContentValues();
        itemValues.put("time",item.getTime());
        itemValues.put("goodsid",item.getGoodsId());
        itemValues.put("vendor",item.getVendor());
        itemValues.put("model",item.getModel());
        itemValues.put("type",item.getType());
        itemValues.put("memo",item.getMemo());
        itemValues.put("person_id",item.getPerson_id());
        itemValues.put("personname",item.getPersonName());
        itemValues.put("box",item.getBox());
        itemValues.put("action",item.getAction());
        itemValues.put("explain",item.getExplain());
        itemValues.put("number", item.getNumber());

        Cursor c = db.rawQuery("SELECT number FROM box WHERE box=? AND goodsid=?",new String[]{item.getBox(),item.getGoodsId()});
        if (c.getCount()>0){
            c.moveToNext();
            boxValues.put("number", c.getInt(c.getColumnIndex("number")) + item.getNumber());
            db.beginTransaction();
            try {
                db.insert("item", null, itemValues);
                db.update("box", boxValues, "box=? AND goodsid=?", new String[]{item.getBox(), item.getGoodsId()});
                db.setTransactionSuccessful();
                return true;
            }finally {
                c.close();
                db.endTransaction();
            }
        }else {
            boxValues.put("box",item.getBox());
            boxValues.put("goodsid", item.getGoodsId());
            boxValues.put("vendor",item.getVendor());
            boxValues.put("model",item.getModel());
            boxValues.put("type",item.getType());
            boxValues.put("memo",item.getMemo());
            boxValues.put("number", item.getNumber());
            db.beginTransaction();
            try {
                db.insert("item",null,itemValues);
                db.insert("box",null,boxValues);
                db.setTransactionSuccessful();
                return true;
            }finally {
                c.close();
                db.endTransaction();
            }
        }
    }

    public boolean takeout(Item item){

        ContentValues itemValues = new ContentValues();
        ContentValues boxValues = new ContentValues();
        itemValues.put("time",item.getTime());
        itemValues.put("goodsid",item.getGoodsId());
        itemValues.put("vendor",item.getVendor());
        itemValues.put("model",item.getModel());
        itemValues.put("type",item.getType());
        itemValues.put("memo",item.getMemo());
        itemValues.put("person_id",item.getPerson_id());
        itemValues.put("personname",item.getPersonName());
        itemValues.put("box",item.getBox());
        itemValues.put("action",item.getAction());
        itemValues.put("explain",item.getExplain());
        itemValues.put("number", item.getNumber());
        Cursor c = db.rawQuery("SELECT _id,number FROM box WHERE box=? AND goodsid=?",new String[]{item.getBox(),item.getGoodsId()});
        if (c.getCount()>0){
            c.moveToNext();
            if (c.getInt(c.getColumnIndex("number"))<item.getNumber()){
                return false;
            }else if (c.getInt(c.getColumnIndex("number")) == item.getNumber()){
                db.beginTransaction();
                try {
                    db.insert("item",null,itemValues);
                    db.delete("box","_id=?",new String[]{String.valueOf(c.getInt(c.getColumnIndex("_id")))});
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                return true;
            }else {
                boxValues.put("number", c.getInt(c.getColumnIndex("number")) - item.getNumber());
                db.beginTransaction();
                try {
                    db.insert("item", null, itemValues);
                    db.update("box", boxValues, "box=? AND goodsid=?", new String[]{item.getBox(), item.getGoodsId()});
                    db.setTransactionSuccessful();
                    return true;
                }finally {
                    c.close();
                    db.endTransaction();
                }
            }
        }else {
            return false;
        }
    }


    public List<Box> queryBoxByBox(String boxid){
        List<Box> boxes = new LinkedList<Box>();
        Cursor c = db.rawQuery("SELECT *  FROM box WHERE box=?", new String[]{boxid});
        while (c.moveToNext()){
            Box box = new Box((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getInt(c.getColumnIndex("number")));
            boxes.add(box);
        }
        c.close();
        return boxes;
    }

    public List<Box> queryBoxByGoodsid(String goodsid1){
        List<Box> boxList = new LinkedList<Box>();
        goodsid1 = "%"+goodsid1+"%";
        Cursor c = db.rawQuery("SELECT * FROM box WHERE goodsid LIKE ?",new String[]{goodsid1});
        while (c.moveToNext()){
            Box box = new Box((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getInt(c.getColumnIndex("number")));
            boxList.add(box);
        }
        c.close();
        return boxList;
    }

    public List<Box> queryBoxByGoodsid(String goodsid1,String goodsid2){
        List<Box> boxList = new LinkedList<Box>();
        Cursor c = db.rawQuery("SELECT * FROM box WHERE goodsid LIKE ? AND goodsid LIKE ?",new String[]{"%"+goodsid1+"%","%"+goodsid2+"%"});
        while (c.moveToNext()){
            Box box = new Box((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getInt(c.getColumnIndex("number")));
            boxList.add(box);
        }
        c.close();
        return boxList;
    }
    public List<Box> queryBoxByGoodsid(String goodsid1,String goodsid2,String goodsid3){
        List<Box> boxList = new LinkedList<Box>();
        Cursor c = db.rawQuery("SELECT * FROM box WHERE goodsid LIKE ? AND goodsid LIKE ? AND goodsid LIKE ?",new String[]{"%"+goodsid1+"%","%"+goodsid2+"%","%"+goodsid3+"%"});
        while (c.moveToNext()){
            Box box = new Box((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getInt(c.getColumnIndex("number")));
            boxList.add(box);
        }
        c.close();
        return boxList;
    }
    public List<Box> queryBoxByGoodsid(String goodsid1,String goodsid2,String goodsid3,String goodsid4){
        List<Box> boxList = new LinkedList<Box>();
        Cursor c = db.rawQuery("SELECT * FROM box WHERE goodsid LIKE ? AND goodsid LIKE ? AND goodsid LIKE ? AND goodsid LIKE ?",new String[]{"%"+goodsid1+"%","%"+goodsid2+"%","%"+goodsid3+"%","%"+goodsid4+"%"});
        while (c.moveToNext()){
            Box box = new Box((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getInt(c.getColumnIndex("number")));
            boxList.add(box);
        }
        c.close();
        return boxList;
    }


    public int getItemCount(){
        Cursor c = db.rawQuery("SELECT * FROM item ORDER BY _id DESC",null);
        int count = c.getCount();
        c.close();
        return count;
    }

    public List<Item> getItem(int pageSize,int pageNum){
        List<Item> list = new LinkedList<Item>();
        Cursor c = db.rawQuery("SELECT * FROM item ORDER BY _id DESC LIMIT ? OFFSET ?", new String[]{String.valueOf(pageSize), String.valueOf((pageNum - 1) * pageSize)});
        while (c.moveToNext()){
            Item item = new Item((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("time")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getString(c.getColumnIndex("person_id")),c.getString(c.getColumnIndex("personname")),
                    c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("action")),c.getString(c.getColumnIndex("explain")),
                    c.getInt(c.getColumnIndex("number")));
            list.add(item);
        }
        c.close();
        return list;
    }

    public List<Item> getItem(){
        List<Item> list = new LinkedList<Item>();
        Cursor c = db.rawQuery("SELECT * FROM item ORDER BY _id DESC",null);
        while (c.moveToNext()){
            Item item = new Item((long)c.getInt(c.getColumnIndex("_id")),c.getString(c.getColumnIndex("time")),c.getString(c.getColumnIndex("goodsid")),
                    c.getString(c.getColumnIndex("vendor")),c.getString(c.getColumnIndex("model")),c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("memo")),c.getString(c.getColumnIndex("person_id")),c.getString(c.getColumnIndex("personname")),
                    c.getString(c.getColumnIndex("box")),c.getString(c.getColumnIndex("action")),c.getString(c.getColumnIndex("explain")),
                    c.getInt(c.getColumnIndex("number")));
            list.add(item);
        }
        c.close();
        return list;
    }
    

}
