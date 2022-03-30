package fi.plasmonics.inventory.dto;

import org.springframework.util.StringUtils;

import java.io.Serializable;

import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.model.response.container.ContainerModel;

public class ContainerModelDto implements Serializable {

    private Container container;

    public ContainerModelDto(Container container) {
        this.container = container;
    }

    public ContainerModel toModel(){
        ContainerModel containerModel = new ContainerModel();
        containerModel.setId(container.getId().toString());
        containerModel.setContainerState(container.getContainerState().toString());
        containerModel.setBarcode(container.getBarcode());
        containerModel.setVolume(container.getVolume().toString());
        containerModel.setProductName(container.getProduct().getName());
        if(container.getCreateTime() != null){
            containerModel.setCreateTime(container.getCreateTime().toString());

        }
        if(container.getOpenTime() != null){
            containerModel.setOpenTime(container.getOpenTime().toString());

        }
        if(container.getReadyToDispatchTime() != null){
            containerModel.setReadyToDispatchTime(container.getReadyToDispatchTime().toString());

        }

        if(container.getDispatchedTime() != null){
            containerModel.setDispatchedTime(container.getDispatchedTime().toString());

        }

        if(StringUtils.hasText(container.getDispatchedBy())){
            containerModel.setDispatchedBy(container.getDispatchedBy());
        }


        if(StringUtils.hasText(container.getReadyToDispatchBy())){
            containerModel.setReadyToDispatchBy(container.getReadyToDispatchBy());
        }


        return containerModel;

    }
}
