package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Item implements Serializable {
    private long _id;
    private String time;
    private String goodsId;
    private String vendor;
    private String model;
    private String type;
    private String memo;
    private String person_id;
    private String personName;
    private String box;
    private String action;
    private String explain;
    private int number;

    public Item(String type, String box, int number) {
        this.type = type;
        this.box = box;
        this.number = number;
    }

    public Item( String vendor, String model, String type, String memo, Person person, String action,int number) {
        this.goodsId = vendor+"_"+model+"_"+type+"_"+memo;
        this.vendor = vendor;
        this.model = model;
        this.type = type;
        this.memo = memo;
        this.person_id = String.valueOf(person.get_id());
        this.personName = person.getName();
        this.action = action;
        this.number = number;
    }

    public Item(long _id, String time, String goodsId, String vendor, String model, String type, String memo, String person_id, String personName, String box, String action, String explain,int number) {
        this._id = _id;
        this.time = time;
        this.goodsId = goodsId;
        this.vendor = vendor;
        this.model = model;
        this.type = type;
        this.memo = memo;
        this.person_id = person_id;
        this.personName = personName;
        this.box = box;
        this.action = action;
        this.explain = explain;
    }


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public String toString(){
        return "_id:"+_id+" time:"+time+" goodsId:"+goodsId+" person id:"+person_id+" personname:"+personName+
                " box:"+box+ " action:"+action +" explain:"+explain+" number:"+number+"\n";
    }
}
