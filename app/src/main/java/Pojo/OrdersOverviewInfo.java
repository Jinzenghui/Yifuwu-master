package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BIAC on 2016/3/22.
 */
public class OrdersOverviewInfo implements Parcelable {

    private int result;
    private String text;
    private OrdersData data;

    public OrdersOverviewInfo(){

    }

    private OrdersOverviewInfo(Parcel in){
        this.result = in.readInt();
        this.text = in.readString();
        this.data = in.readParcelable(OrdersOverviewInfo.class.getClassLoader());
    }

    public int getResult(){
        return this.result;
    }

    public void setResult(int result){
        this.result = result;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public OrdersData getData(){
        return this.data;
    }

    public void setData(OrdersData data){
        this.data = data;
    }

    public static final Parcelable.Creator<OrdersOverviewInfo> CREATOR = new Parcelable.Creator<OrdersOverviewInfo>(){

        @Override
        public OrdersOverviewInfo createFromParcel(Parcel source) {
            return new OrdersOverviewInfo(source);
        }

        @Override
        public OrdersOverviewInfo[] newArray(int size) {
            return new OrdersOverviewInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.result);
        dest.writeString(this.text);
        dest.writeParcelable(data, flags);

    }

    public static class OrdersData implements Parcelable{

        private int new_work_orders;
        private int processing_work_orders;
        private int completed_work_orders;

        public OrdersData(){

        }

        private OrdersData(Parcel in){

            this.new_work_orders = in.readInt();
            this.processing_work_orders = in.readInt();
            this.completed_work_orders = in.readInt();

        }



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeInt(this.new_work_orders);
            dest.writeInt(this.processing_work_orders);
            dest.writeInt(this.completed_work_orders);

        }

        public int getNew_work_orders(){
            return new_work_orders;
        }

        public void setNew_work_orders(int new_work_orders){
            this.new_work_orders = new_work_orders;
        }

        public int getProcessing_work_orders(){
            return processing_work_orders;
        }

        public void setProcessing_work_orders(int processing_work_orders){
            this.processing_work_orders = processing_work_orders;
        }

        public int getCompleted_work_orders(){
            return completed_work_orders;
        }

        public void setCompleted_work_orders(int completed_work_orders){
            this.completed_work_orders = completed_work_orders;
        }

        public static final Parcelable.Creator<OrdersData> CREATOR = new Parcelable.Creator<OrdersData>(){


            @Override
            public OrdersData createFromParcel(Parcel source) {
                return new OrdersData(source);
            }

            @Override
            public OrdersData[] newArray(int size) {
                return new OrdersData[size];
            }
        };

    }

}
