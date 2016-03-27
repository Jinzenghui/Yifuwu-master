package Pojo;

/**
 * Created by BIAC on 2016/3/27.
 */
public class AcceptOrderMessage {

    private int result;
    private String text;

    public AcceptOrderMessage(){

    }

    public void setResult(int result){

        this.result = result;

    }

    public int getResult(){

        return result;

    }

    public void setText(String text){

        this.text = text;

    }

    public String getText(){

        return text;

    }

}
