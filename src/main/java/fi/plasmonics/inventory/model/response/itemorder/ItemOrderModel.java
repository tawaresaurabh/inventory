package fi.plasmonics.inventory.model.response.itemorder;

import java.io.Serializable;

public class ItemOrderModel implements Serializable {
    private  String id;
    private  String quantity;
    private  String createTime;
    private  String action;
    private  String enteredBy;


    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getAction() {
        return action;
    }

    public String getEnteredBy() {
        return enteredBy;
    }
}
