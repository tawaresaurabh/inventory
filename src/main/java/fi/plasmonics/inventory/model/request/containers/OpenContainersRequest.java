package fi.plasmonics.inventory.model.request.containers;

import java.io.Serializable;
import java.util.List;

public class OpenContainersRequest implements Serializable {
    private List<String> containerIdList;


    public List<String> getContainerIdList() {
        return containerIdList;
    }

    public void setContainerIdList(List<String> containerIdList) {
        this.containerIdList = containerIdList;
    }
}
