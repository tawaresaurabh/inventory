package fi.plasmonics.inventory.model.request.product;

import java.io.Serializable;
import java.util.List;

public class CreateItem implements Serializable {
    private String name;
    private String unitOfMeasure;
    private String description;
    private String thresholdQuantity;
    private List<String> notificationEmails;



    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThresholdQuantity() {
        return thresholdQuantity;
    }

    public void setThresholdQuantity(String thresholdQuantity) {
        this.thresholdQuantity = thresholdQuantity;
    }

    public List<String> getNotificationEmails() {
        return notificationEmails;
    }

    public void setNotificationEmails(List<String> notificationEmails) {
        this.notificationEmails = notificationEmails;
    }
}
