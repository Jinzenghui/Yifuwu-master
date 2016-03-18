package Pojo;

/**
 * Created by fanlei on 16/3/15.
 */
public class UserLogin {

    public UserLogin(String userName, String userPassword){
        this.user_name = userName;
        this.user_password = userPassword;
    }

    public String user_name;
    public String user_password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
