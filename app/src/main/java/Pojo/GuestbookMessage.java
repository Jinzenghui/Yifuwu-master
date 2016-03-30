package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BIAC on 2016/3/30.
 */
public class GuestbookMessage implements Parcelable{

    private int result;
    private String text;
    private List<BookMessage> data = new ArrayList<BookMessage>();
    //private BookMessage data;

    public GuestbookMessage(){

    }

    private GuestbookMessage(Parcel in){

        this.result = in.readInt();
        this.text = in.readString();
        //this.data = in.readParcelable(BookMessage.class.getClassLoader());
        this.data = in.readArrayList(BookMessage.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(result);
        dest.writeString(text);
        dest.writeList(data);

    }

    public static final Parcelable.Creator<GuestbookMessage> CREATOR = new Parcelable.Creator<GuestbookMessage>(){

        @Override
        public GuestbookMessage createFromParcel(Parcel source) {
            return new GuestbookMessage(source);
        }

        @Override
        public GuestbookMessage[] newArray(int size) {
            return new GuestbookMessage[size];
        }
    };

    public void setResult(int result){
        this.result = result;
    }

    public int getResult(){
        return this.result;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public void setData(List<BookMessage> data){
        this.data = data;
    }

    public List<BookMessage> getData(){
        return this.data;
    }

    public static class BookMessage implements Parcelable{

        private String message_time;
        private String message_content;
        private String message_type;

        public BookMessage(){

        }

        protected BookMessage(Parcel in){

            this.message_time = in.readString();
            this.message_content = in.readString();
            this.message_type = in.readString();

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(this.message_time);
            dest.writeString(this.message_content);
            dest.writeString(this.message_type);

        }

        public static final Creator<BookMessage> CREATOR = new Creator<BookMessage>() {
            @Override
            public BookMessage createFromParcel(Parcel source) {
                return new BookMessage(source);
            }

            @Override
            public BookMessage[] newArray(int size) {
                return new BookMessage[size];
            }
        };

        public void setMessage_time(String message_time){
            this.message_time = message_time;
        }

        public String getMessage_time(){

            return message_time;
        }

        public void setMessage_content(String message_content){

            this.message_content = message_content;
        }

        public String getMessage_content(){

            return message_content;
        }

        public void setMessage_type(String message_type){

            this.message_type = message_type;

        }

        public String getMessage_type(){

            return message_type;
        }

    }

}
