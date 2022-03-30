package fi.plasmonics.inventory.model.request.containers;

import java.io.Serializable;
import java.util.List;

public class ReadyToDispatchContainersRequest implements Serializable {
    private String readyToDispatchBy;
    private List<ReadyToDispatchContainerItem> readyToDispatchContainerItemList;

    public List<ReadyToDispatchContainerItem> getReadyToDispatchContainerItemList() {
        return readyToDispatchContainerItemList;
    }

    public void setReadyToDispatchContainerItemList(List<ReadyToDispatchContainerItem> readyToDispatchContainerItemList) {
        this.readyToDispatchContainerItemList = readyToDispatchContainerItemList;
    }

    public String getReadyToDispatchBy() {
        return readyToDispatchBy;
    }

    public void setReadyToDispatchBy(String readyToDispatchBy) {
        this.readyToDispatchBy = readyToDispatchBy;
    }
}
