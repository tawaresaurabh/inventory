package fi.plasmonics.inventory.model.response.container;

import java.io.Serializable;
import java.util.List;

public class GetContainersResponse implements Serializable {
    public List<ContainerModel> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<ContainerModel> containerList) {
        this.containerList = containerList;
    }

    private List<ContainerModel> containerList;

}
