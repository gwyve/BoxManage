package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/25.
 */
public class Box implements Serializable,Comparable{
    private long _id;
    private String box;
    private String goodsId;
    private String vendor;
    private String model;
    private String type;
    private String memo;
    private int number;


    public Box(long _id, String box, String goodsId, String vendor, String model, String type, String memo, int number) {
        this._id = _id;
        this.box = box;
        this.goodsId = goodsId;
        this.vendor = vendor;
        this.model = model;
        this.type = type;
        this.memo = memo;
        this.number = number;
    }

    public Box(String box, String goodsid, String vendor, String model, String type, String memo, int anInt) {
        this.box = box;
        this.goodsId = goodsid;
        this.vendor = vendor;
        this.model = model;
        this.type = type;
        this.memo = memo;
        this.number = anInt;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString(){
        return "_id:" +_id+" box:"+box+" goodsid:"+goodsId+" vendor:"+ vendor +" model:" + model + " type:"
                +type + " memo:"+memo +" number:"+number;
    }


    @Override
    public int compareTo(Object another) {
        if (Integer.parseInt(this.getBox()) < Integer.parseInt(((Box)another).getBox()))
        {
            return -1;
        }else if (Integer.parseInt(this.getBox()) > Integer.parseInt(((Box)another).getBox())){
            return 1;
        }else {
            return 0;
        }
    }
}
