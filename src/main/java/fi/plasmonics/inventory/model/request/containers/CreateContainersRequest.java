package fi.plasmonics.inventory.model.request.containers;

import java.io.Serializable;
import java.util.List;



public class CreateContainersRequest implements Serializable {

    private List<ContainerItem> containerItemList;

    public List<ContainerItem> getContainerItemList() {
        return containerItemList;
    }

    public void setContainerItemList(List<ContainerItem> containerItemList) {
        this.containerItemList = containerItemList;
    }
}