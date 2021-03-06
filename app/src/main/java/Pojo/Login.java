package Pojo;

/**
 * Created by fanlei on 16/3/15.
 */
public class Login {

    public int result;
    public String text;
    public Data data;
    public class Data{
        int user_id;

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int userId) {
            this.user_id = userId;
        }
    }

    public Login(int result, String text, Data data) {
        this.result = result;
        this.text = text;
        this.data = data;
    }

    @Override
    public String toString(){
        if(data!=null) {
            return result + text + data.user_id;
        }
        return result+text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
