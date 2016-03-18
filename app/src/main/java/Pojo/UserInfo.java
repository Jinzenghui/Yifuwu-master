package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fanlei on 16/3/16.
 */
public class UserInfo implements Parcelable{

    private int result;
    private String text;
    private User user;

    public UserInfo() {
    }

    private UserInfo(Parcel in){
        this.user = in.readParcelable(User.class.getClassLoader());
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
        dest.writeParcelable(user, flags);

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
