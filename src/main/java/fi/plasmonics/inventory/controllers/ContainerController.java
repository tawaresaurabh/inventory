package fi.plasmonics.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import fi.plasmonics.inventory.dto.ContainerModelDto;
import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.model.request.containers.ContainerItem;
import fi.plasmonics.inventory.model.request.containers.CreateContainersRequest;
import fi.plasmonics.inventory.model.request.containers.DispatchContainersRequest;
import fi.plasmonics.inventory.model.request.containers.OpenContainersRequest;
import fi.plasmonics.inventory.model.request.containers.ReadyToDispatchContainerItem;
import fi.plasmonics.inventory.model.request.containers.ReadyToDispatchContainersRequest;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.model.response.container.ContainerActionResponse;
import fi.plasmonics.inventory.model.response.container.ContainerModel;
import fi.plasmonics.inventory.model.response.container.CreateContainersResponse;
import fi.plasmonics.inventory.model.response.container.GetContainersResponse;
import fi.plasmonics.inventory.services.ContainerService;
import fi.plasmonics.inventory.services.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ContainerController {

    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    @Autowired
    private ContainerService containerService;

    @Autowired
    private ProductService productService;




    @GetMapping("/containers")
    public GetContainersResponse getContainers(){
       List<Container> containerList = containerService.getContainers();
       List<ContainerModel> containerModelList = containerList.stream().map(container -> new ContainerModelDto(container).toModel()).collect(Collectors.toList());
       GetContainersResponse getContainersResponse = new GetContainersResponse();
       getContainersResponse.setContainerList(containerModelList);
       return getContainersResponse;
    }


    @GetMapping("/containers/{state}")
    public GetContainersResponse getContainersByState(@PathVariable String state){
        List<Container> containerList =  containerService.getContainerByState(Enum.valueOf(ContainerState.class,state));
        List<ContainerModel> containerModelList = containerList.stream().map(container -> new ContainerModelDto(container).toModel()).collect(Collectors.toList());
        GetContainersResponse getContainersResponse = new GetContainersResponse();
        getContainersResponse.setContainerList(containerModelList);
        return getContainersResponse;

    }

    @GetMapping("/containers/barcode/{barcode}")
    public ContainerModel getContainerByBarcode(@PathVariable String barcode){
        Container container = containerService.findByBarcode(barcode);
        ContainerModel containerModel = null;
        if(container !=null){
            containerModel = new ContainerModelDto(container).toModel();
        }
        return containerModel;
    }


    @GetMapping("/containers/{id}")
    public ContainerModel getContainerById(@PathVariable String id){
        Container container = containerService.findById(Long.parseLong(id));
        ContainerModel containerModel = null;
        if(container !=null){
            containerModel = new ContainerModelDto(container).toModel();
        }
        return containerModel;
    }



    @PostMapping("/containers/new")
    public CreateContainersResponse createContainers(@RequestBody CreateContainersRequest createContainersRequest) {
        List<Container> containerList = new ArrayList<>();
        for(ContainerItem containerItem : createContainersRequest.getContainerItemList()){
            for(int i = 0 ; i < Integer.parseInt(containerItem.getQuantity()) ; i ++){
                Container container  = new Container();
                container.setContainerState(ContainerState.NEW);
                container.setCreateTime(Timestamp.from(Instant.now()));
                container.setVolume(new BigDecimal(containerItem.getVolume()));
                Product product = productService.getProductById(Long.parseLong(containerItem.getProductId()));
                container.setProduct(product);
                containerList.add(container);
            }
        }

        List<Container> containerList1 = containerService.saveAll(containerList);
        List<ContainerModel> containerModelList = containerList1
                .stream()
                .map(container -> new ContainerModelDto(container).toModel())
                .collect(Collectors.toList());
        CreateContainersResponse createContainersResponse = new CreateContainersResponse();
        createContainersResponse.setContainerList(containerModelList);
        return createContainersResponse;
    }


    @PostMapping("/containers/open")
    public ContainerActionResponse openContainers(@RequestBody OpenContainersRequest openContainersRequest) {
        for(String containerId : openContainersRequest.getContainerIdList()){
            Container container  =  containerService.findById(Long.parseLong(containerId));
            if(container != null){
                container.setOpenTime(Timestamp.from(Instant.now()));
                container.setContainerState(ContainerState.OPEN);
                containerService.save(container);
                return new ContainerActionResponse(MessageType.SUCCESS,"Container is now in Open state");
            }
        }
        return new ContainerActionResponse(MessageType.FAILURE, SOMETHING_WENT_WRONG);
    }


    @PostMapping("/containers/readyToDispatch")
    public ContainerActionResponse readyToDispatchContainers(@RequestBody ReadyToDispatchContainersRequest readyToDispatchContainersRequest) {
        for(ReadyToDispatchContainerItem readyToDispatchContainerItem : readyToDispatchContainersRequest.getReadyToDispatchContainerItemList()){
            Container container  =  containerService.findById(Long.parseLong(readyToDispatchContainerItem.getContainerId()));
            if(containerService.isContainerWithBarcode(readyToDispatchContainerItem.getBarcode())){
                return new ContainerActionResponse(MessageType.FAILURE,"Barcode already exists in the system, Please try new barcode");
            }else{
                container.setBarcode(readyToDispatchContainerItem.getBarcode());
                container.setReadyToDispatchTime(Timestamp.from(Instant.now()));
                container.setReadyToDispatchBy(readyToDispatchContainersRequest.getReadyToDispatchBy());
                container.setContainerState(ContainerState.READY_TO_DISPATCH);
                Product product = productService.getProductById(Long.parseLong(readyToDispatchContainerItem.getProductId()));
                container.setProduct(product);
                containerService.save(container);
                return new ContainerActionResponse(MessageType.SUCCESS,"Container is now in Ready To Dispatch state");
            }

        }
        return new ContainerActionResponse(MessageType.FAILURE,SOMETHING_WENT_WRONG);
    }


    @PostMapping("/containers/dispatch")
    public ContainerActionResponse dispatchContainers(@RequestBody DispatchContainersRequest dispatchContainersRequest) {
        for(String barcode : dispatchContainersRequest.getBarcodeList()){
            Container container  =  containerService.findByBarcode(barcode);
            if(container != null){
                if(container.getContainerState().equals(ContainerState.READY_TO_DISPATCH)){
                    container.setDispatchedTime(Timestamp.from(Instant.now()));
                    container.setContainerState(ContainerState.DISPATCHED);
                    container.setDispatchedBy(dispatchContainersRequest.getDispatchedBy());
                    containerService.save(container);
                    return new ContainerActionResponse(MessageType.SUCCESS,"Container is now in Dispatched state");
                }else{
                    return new ContainerActionResponse(MessageType.FAILURE,"Container is not in Ready to Dispatch state");
                }

            }else{
                return new ContainerActionResponse(MessageType.FAILURE,"System could not find scanned barcode, Please try again");
            }
        }
        return new ContainerActionResponse(MessageType.FAILURE,SOMETHING_WENT_WRONG);
    }


    @PostMapping("/containers/{id}/barcode/{barcode}")
    public void dispatchContainers(@PathVariable String id, @PathVariable String barcode) {
            Container container  =  containerService.findById(Long.parseLong(id));
            container.setBarcode(barcode);
            containerService.save(container);
    }


    @DeleteMapping("/containers/{id}")
    public void deleteContainer(@PathVariable String id) {
        containerService.deleteContainer(Long.parseLong(id));
    }






}
