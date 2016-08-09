package DB;

/**
 * Created by YoungJung on 2016-08-08.
 */
public class User_Info {
    private int _id;
    private String name;
    private String tel;
    private String car_num;

    User_Info(){}

    public User_Info(int _id, String name, String tel, String car_num) {
        this._id = _id;
        this.name = name;
        this.tel = tel;
        this.car_num = car_num;
    }

    public User_Info(String name, String tel, String car_num) {
        this.name = name;
        this.tel = tel;
        this.car_num = car_num;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }
}
