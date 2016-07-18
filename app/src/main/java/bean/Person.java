package bean;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Person {

    private long _id;
    private String id;
    private String name;


    public Person(long _id, String id, String name) {
        this._id = _id;
        this.id = id;
        this.name = name;
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
