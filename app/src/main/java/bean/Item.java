package bean;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Item {
    private long _id;
    private String time;
    private String goodsid;
    private String personid;
    private String box;
    private String action;
    private String memo;

    public Item(long _id, String time, String goodsid, String personid, String box, String action, String memo) {
        this._id = _id;
        this.time = time;
        this.goodsid = goodsid;
        this.personid = personid;
        this.box = box;
        this.action = action;
        this.memo = memo;
    }

    public Item(String time, String goodsid, String personid, String box, String action, String memo) {
        this.time = time;
        this.goodsid = goodsid;
        this.personid = personid;
        this.box = box;
        this.action = action;
        this.memo = memo;
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

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
