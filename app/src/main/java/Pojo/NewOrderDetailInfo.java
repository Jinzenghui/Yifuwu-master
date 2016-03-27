package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BIAC on 2016/3/23.
 */
public class NewOrderDetailInfo implements Parcelable {

    private int result;
    private String text;
    private NewOrderDetail data;

    public NewOrderDetailInfo(){

    }

    private NewOrderDetailInfo(Parcel in){
        this.result = in.readInt();
        this.text = in.readString();
        this.data = in.readParcelable(NewOrderDetail.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(result);
        dest.writeString(text);
        dest.writeParcelable(data, flags);

    }

    public static final Parcelable.Creator<NewOrderDetailInfo> CREATOR = new Parcelable.Creator<NewOrderDetailInfo>(){

        @Override
        public NewOrderDetailInfo createFromParcel(Parcel source) {
            return new NewOrderDetailInfo(source);
        }

        @Override
        public NewOrderDetailInfo[] newArray(int size) {
            return new NewOrderDetailInfo[size];
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

    public void setData(NewOrderDetail data){
        this.data = data;
    }

    public NewOrderDetail getData(){
        return this.data;
    }

    public static class NewOrderDetail implements Parcelable{

        private String work_order_id;
        private String work_order_level;
        private String create_time;
        private String remaining_time;
        private String work_order_type_code;
        private String complaint_tele_num;
        private String complaint_time;
        private String complaint_position;
        private String message;
        private String gps_lon;
        private String gps_lat;
        private String pre_deal_result;

        public NewOrderDetail(){

        }

        protected NewOrderDetail(Parcel in){

            this.work_order_id = in.readString();
            this.work_order_level = in.readString();
            this.create_time = in.readString();
            this.remaining_time = in.readString();
            this.work_order_type_code = in.readString();
            this.complaint_tele_num = in.readString();
            this.complaint_time = in.readString();
            this.complaint_position = in.readString();
            this.message = in.readString();
            this.gps_lon = in.readString();
            this.gps_lat = in.readString();
            this.pre_deal_result = in.readString();

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(this.work_order_id);
            dest.writeString(this.work_order_level);
            dest.writeString(this.create_time);
            dest.writeString(this.remaining_time);
            dest.writeString(this.work_order_type_code);
            dest.writeString(this.complaint_tele_num);
            dest.writeString(this.complaint_time);
            dest.writeString(this.complaint_position);
            dest.writeString(this.message);
            dest.writeString(this.gps_lon);
            dest.writeString(this.gps_lat);
            dest.writeString(this.pre_deal_result);

        }

        public static final Creator<NewOrderDetail> CREATOR = new Creator<NewOrderDetail>(){

            @Override
            public NewOrderDetail createFromParcel(Parcel source) {
                return new NewOrderDetail(source);
            }

            @Override
            public NewOrderDetail[] newArray(int size) {
                return new NewOrderDetail[size];
            }
        };

        public void setWork_order_id(String work_order_id){

            this.work_order_id = work_order_id;

        }

        public String getWork_order_id(){

            return this.work_order_id;

        }

        public void setWork_order_level(String work_order_level){

            this.work_order_level = work_order_level;

        }

        public String getWork_order_level(){

            return this.work_order_level;

        }

        public void setCreate_time(String create_time){
            this.create_time = create_time;
        }

        public String getCreate_time(){
            return this.create_time;
        }

        public void setRemaining_time(String remaining_time){
            this.remaining_time = remaining_time;
        }

        public String getRemaining_time(){
            return this.remaining_time;
        }

        public void setWork_order_type_code(String work_order_type_code){
            this.work_order_type_code = work_order_type_code;
        }

        public String getWork_order_type_code(){
            return this.work_order_type_code;
        }

        public void setComplaint_tele_num(String complaint_tele_num){
            this.complaint_tele_num = complaint_tele_num;
        }

        public String getComplaint_tele_num(){
            return this.complaint_tele_num;
        }

        public void setComplaint_time(String complaint_time){
            this.complaint_time = complaint_time;
        }

        public String getComplaint_time(){
            return complaint_time;
        }

        public void setComplaint_position(String complaint_position){
            this.complaint_position = complaint_position;
        }

        public String getComplaint_position(){
            return this.complaint_position;
        }

        public void setMessage(String message){
            this.message = message;
        }

        public String getMessage(){
            return this.message;
        }

        public void setGps_lon(String gps_lon){
            this.gps_lon = gps_lon;
        }

        public String getGps_lon(){
            return gps_lon;
        }

        public void setGps_lat(String gps_lat){
            this.gps_lat = gps_lat;
        }

        public String getGps_lat(){
            return this.gps_lat;
        }

        public void setPre_deal_result(String pre_deal_result){
            this.pre_deal_result = pre_deal_result;
        }

        public String getPre_deal_result(){
            return this.pre_deal_result;
        }

    }

}
