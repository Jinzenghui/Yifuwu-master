package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fanlei on 16/3/16.
 */
public class UserInfo implements Parcelable{

    private int result;
    private String text;
    private User data;

    public UserInfo() {
    }

    private UserInfo(Parcel in){
        this.data = in.readParcelable(User.class.getClassLoader());
        this.result = in.readInt();
        this.text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(text);
        dest.writeInt(result);
        dest.writeParcelable(data, flags);

    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>(){
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };


    public static class User implements Parcelable {

        private int user_id;
        private int total_login_times;
        private int total_work_orders;
        private int points;
        private int user_rank;

        private int total_login_days;
        private String last_login_time;
        private String work_station;

        public User() {


        }

        private User(Parcel in){
            this.user_id = in.readInt();
            this.total_login_days = in.readInt();
            this.total_login_times = in.readInt();
            this.total_work_orders = in.readInt();
            this.points = in.readInt();
            this.last_login_time = in.readString();
            this.work_station = in.readString();
        }



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeInt(this.user_id);
            dest.writeInt(total_login_days);
            dest.writeInt(total_login_times);
            dest.writeInt(total_work_orders);
            dest.writeInt(this.points);
            dest.writeString(last_login_time);
            dest.writeString(this.work_station);

        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getTotal_login_times() {
            return total_login_times;
        }

        public void setTotal_login_times(int total_login_times) {
            this.total_login_times = total_login_times;
        }

        public int getTotal_work_orders() {
            return total_work_orders;
        }

        public void setTotal_work_orders(int total_work_orders) {
            this.total_work_orders = total_work_orders;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getUser_rank() {
            return user_rank;
        }

        public void setUser_rank(int user_rank) {
            this.user_rank = user_rank;
        }

        public int getTotal_login_days() {
            return total_login_days;
        }

        public void setTotal_login_days(int total_login_days) {
            this.total_login_days = total_login_days;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getWork_station() {
            return work_station;
        }

        public void setWork_station(String work_station) {
            this.work_station = work_station;
        }

        public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){

            @Override
            public User createFromParcel(Parcel source) {
                return new User(source);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
    }
}
