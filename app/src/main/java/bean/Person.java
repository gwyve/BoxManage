package bean;

/**
 * Created by Administrator on 2016/7/19.
 */
public class Person {

    private long _id;
    private String id;
    private String name;
    private String password;


    public Person(long _id, String id, String name,String password) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Person(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public  Person(String id,String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "_id:"+id+"\nId:"+id+"\nname:"+name+"\npassword:"+password;
    }
}
