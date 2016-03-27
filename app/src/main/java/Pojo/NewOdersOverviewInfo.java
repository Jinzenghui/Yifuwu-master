package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BIAC on 2016/3/23.
 */
public class NewOdersOverviewInfo implements Parcelable {

    private int result;
    private String text;
    private int sum;
    private List<OrderOverview> data = new ArrayList<OrderOverview>();

    public NewOdersOverviewInfo(){

    }

    private NewOdersOverviewInfo(Parcel in){

        this.result = in.readInt();
        this.text = in.readString();
        this.sum = in.readInt();
        this.data = in.readArrayList(OrderOverview.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(result);
        dest.writeString(text);
        dest.writeInt(sum);
        dest.writeList(data);

    }

    public static final Parcelable.Creator<NewOdersOverviewInfo> CREATOR = new Parcelable.Creator<NewOdersOverviewInfo>(){

        @Override
        public NewOdersOverviewInfo createFromParcel(Parcel source) {
            return new NewOdersOverviewInfo(source);
        }

        @Override
        public NewOdersOverviewInfo[] newArray(int size) {
            return new NewOdersOverviewInfo[size];
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

    public void setSum(int sum){
        this.sum = sum;
    }

    public int getSum(){
        return this.sum;
    }

    public void setData(List<OrderOverview> data){
        this.data = data;
    }

    public List<OrderOverview> getData(){
        return this.data;
    }

    public static class OrderOverview implements Parcelable{

        private String work_order_id;
        private String creat_time;
        private String work_order_type;
        private String area;

        public OrderOverview(){

        }

        protected OrderOverview(Parcel in) {
            this.work_order_id = in.readString();
            this.creat_time = in.readString();
            this.work_order_type = in.readString();
            this.area = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.work_order_id);
            dest.writeString(this.creat_time);
            dest.writeString(this.work_order_type);
            dest.writeString(this.area);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OrderOverview> CREATOR = new Creator<OrderOverview>() {
            @Override
            public OrderOverview createFromParcel(Parcel in) {
                return new OrderOverview(in);
            }

            @Override
            public OrderOverview[] newArray(int size) {
                return new OrderOverview[size];
            }
        };

        public void setWork_order_id(String work_order_id){

            this.work_order_id = work_order_id;

        }

        public String getWork_order_id(){

            return this.work_order_id;
        }

        public void setCreate_time(String create_time){
            this.creat_time = create_time;
        }

        public String getCreate_time(){

            return this.creat_time;

        }

        public void setWork_order_type(String work_order_type){

            this.work_order_type = work_order_type;

        }

        public String getWork_order_type(){

            return this.work_order_type;

        }

        public void setArea(String area){

            this.area = area;

        }

        public String getArea(){
            return this.area;
        }

    }
}
