package fi.plasmonics.inventory.model.request.containers;

import java.io.Serializable;
import java.util.List;

public class DispatchContainersRequest implements Serializable {

    private List<String> barcodeList;
    private String dispatchedBy;

    public List<String> getBarcodeList() {
        return barcodeList;
    }

    public void setBarcodeList(List<String> barcodeList) {
        this.barcodeList = barcodeList;
    }

    public String getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(String dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }
}
