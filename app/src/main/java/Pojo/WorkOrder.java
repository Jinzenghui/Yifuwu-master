package Pojo;

/**
 * Created by fanlei on 16/3/17.
 */
public class WorkOrder {

    int result;
    String text;
    int new_work_orders;
    int processing_work_orders;
    int completed_work_orders;

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

    public int getNew_work_orders() {
        return new_work_orders;
    }

    public void setNew_work_orders(int new_work_orders) {
        this.new_work_orders = new_work_orders;
    }

    public int getCompleted_work_orders() {
        return completed_work_orders;
    }

    public void setCompleted_work_orders(int completed_work_orders) {
        this.completed_work_orders = completed_work_orders;
    }

    public int getProcessing_work_orders() {
        return processing_work_orders;
    }

    public void setProcessing_work_orders(int processing_work_orders) {
        this.processing_work_orders = processing_work_orders;
    }
}
