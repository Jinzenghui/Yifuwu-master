package Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BIAC on 2016/3/30.
 */
public class PreDealResults implements Parcelable {

    private int result;
    private String text;
    private String pre_deal_results;

    public PreDealResults(){

    }

    private PreDealResults(Parcel in){

        this.result = in.readInt();
        this.text = in.readString();
        this.pre_deal_results = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(result);
        dest.writeString(text);
        dest.writeString(pre_deal_results);

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

    public String getPre_deal_results(){

        return pre_deal_results;
    }

    public void setPre_deal_results(String pre_deal_results){

        this.pre_deal_results = pre_deal_results;
    }

}
