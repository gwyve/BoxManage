package bean;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Goods {

    private long _id;
    private String vendor;
    private String model;
    private String type;
    private String memo;

    public Goods(long _id, String vendor, String model, String type, String memo) {
        this._id = _id;
        this.vendor = vendor;
        this.model = model;
        this.type = type;
        this.memo = memo;
    }

    public Goods(String vendor, String model, String type, String memo) {
        this.vendor = vendor;
        this.model = model;
        this.type = type;
        this.memo = memo;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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
}
