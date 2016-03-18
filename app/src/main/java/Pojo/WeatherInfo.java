package Pojo;

/**
 * Created by fanlei on 16/3/18.
 */
public class WeatherInfo {

    //温度
    public String maxtmp;
    public String mintmp;
    //建议
    public String suggest;
    //城市名
    public String city;
    //空气质量
    public String qlty;

    public String getMaxtmp() {
        return maxtmp;
    }

    public void setMaxtmp(String maxtmp) {
        this.maxtmp = maxtmp;
    }

    public String getMintmp() {
        return mintmp;
    }

    public void setMintmp(String mintmp) {
        this.mintmp = mintmp;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }



    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString(){
        return city+maxtmp+mintmp+qlty+suggest;
    }
}
