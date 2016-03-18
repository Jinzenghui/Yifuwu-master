package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fanlei on 16/3/16.
 */
public class User implements Parcelable {

    private int userId;
    private int totalLoginTimes;
    private int totalWorkOrders;
    private int points;
    private int userRank;

    private int totalLoginDays;
    private String lastLoginTime;
    private String workStation;

    public User() {


    }

    private User(Parcel in){
        this.userId = in.readInt();
        this.totalLoginDays = in.readInt();
        this.totalLoginTimes = in.readInt();
        this.totalWorkOrders = in.readInt();
        this.points = in.readInt();
        this.lastLoginTime = in.readString();
        this.workStation = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.userId);
        dest.writeInt(totalLoginDays);
        dest.writeInt(totalLoginTimes);
        dest.writeInt(totalWorkOrders);
        dest.writeInt(this.points);
        dest.writeString(lastLoginTime);
        dest.writeString(this.workStation);

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalLoginTimes() {
        return totalLoginTimes;
    }

    public void setTotalLoginTimes(int totalLoginTimes) {
        this.totalLoginTimes = totalLoginTimes;
    }

    public int getTotalWorkOrders() {
        return totalWorkOrders;
    }

    public void setTotalWorkOrders(int totalWorkOrders) {
        this.totalWorkOrders = totalWorkOrders;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    public int getTotalLoginDays() {
        return totalLoginDays;
    }

    public void setTotalLoginDays(int totalLoginDays) {
        this.totalLoginDays = totalLoginDays;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation;
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
