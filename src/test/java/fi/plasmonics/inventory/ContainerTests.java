package fi.plasmonics.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import java.util.List;

import fi.plasmonics.inventory.controllers.ContainerController;

import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContainerTests extends TestSetUp {

    @InjectMocks
    ContainerController containerController;

    List<Container> containerList;
    Container newContainer;
    Container openContainer;
    Container readyContainer;
    Container dispatchedContainer;


    @BeforeEach
    void setup() {
        newContainer = createNewContainer();
        openContainer = createOpenContainer();
        readyContainer = createReadyContainer();
        dispatchedContainer = createDispatchedContainer();
        containerList = new ArrayList<>();
        containerList.add(newContainer);
        containerList.add(openContainer);
        containerList.add(readyContainer);
        containerList.add(dispatchedContainer);
    }

    @Test
    void checkGetContainers() {
        when(containerService.getContainers()).thenReturn(containerList);
        GetContainersResponse getContainersResponse = containerController.getContainers();
        assertEquals(4, getContainersResponse.getContainerList().size());
    }

    @Test
    void checkGetContainerByBarcode() {
        when(containerService.findByBarcode(CONTAINER_BARCODE)).thenReturn(readyContainer);
        ContainerModel containerModel = containerController.getContainerByBarcode(CONTAINER_BARCODE);
        assertEquals(READY_CONTAINER_ID.toString(), containerModel.getId());
    }

    @Test
    void checkNewContainerRequest() {
        when(productService.getProductById(RAW_PRODUCT_ID)).thenReturn(createRawProduct());
        List<Container> singleContainerList = new ArrayList<>();
        singleContainerList.add(newContainer);
        when(containerService.saveAll(anyList())).thenReturn(singleContainerList);
        CreateContainersRequest createContainersRequest = new CreateContainersRequest();
        ContainerItem containerItem = new ContainerItem(RAW_PRODUCT_ID.toString(), "10", "1");
        List containerItems = new ArrayList();
        containerItems.add(containerItem);
        createContainersRequest.setContainerItemList(containerItems);
        CreateContainersResponse createContainersResponse = containerController.createContainers(createContainersRequest);
        assertEquals(createContainersResponse.getContainerList().get(0).getContainerState(), ContainerState.NEW.toString());
        assertEquals(createContainersResponse.getContainerList().get(0).getVolume(), newContainer.getVolume().toString());

    }


    @Test
    void checkOpenContainerRequestSuccess() {
        when(containerService.findById(NEW_CONTAINER_ID)).thenReturn(newContainer);
        when(containerService.save(newContainer)).thenReturn(openContainer);
        List<String> singleContainerIdList = new ArrayList<>();
        singleContainerIdList.add(NEW_CONTAINER_ID.toString());
        OpenContainersRequest openContainersRequest = new OpenContainersRequest();
        openContainersRequest.setContainerIdList(singleContainerIdList);
        ContainerActionResponse containerActionResponse = containerController.openContainers(openContainersRequest);
        assertEquals(MessageType.SUCCESS,containerActionResponse.getMessageType());

    }

    @Test
    void checkOpenContainerRequestFailure() {
        when(containerService.findById(FAKE_CONTAINER_ID)).thenReturn(null);
        List<String> singleContainerIdList = new ArrayList<>();
        singleContainerIdList.add(FAKE_CONTAINER_ID.toString());
        OpenContainersRequest openContainersRequest = new OpenContainersRequest();
        openContainersRequest.setContainerIdList(singleContainerIdList);
        ContainerActionResponse containerActionResponse = containerController.openContainers(openContainersRequest);
        assertEquals(MessageType.FAILURE,containerActionResponse.getMessageType());
    }



    @Test
    void checkReadyContainerRequestSuccess() {
        ReadyToDispatchContainersRequest readyToDispatchContainersRequest = new ReadyToDispatchContainersRequest();
        readyToDispatchContainersRequest.setReadyToDispatchBy(TEST_USER);
        List<ReadyToDispatchContainerItem> singleReadyList = new ArrayList<>();
        ReadyToDispatchContainerItem readyToDispatchContainerItem = new ReadyToDispatchContainerItem(openContainer.getId().toString(),CONTAINER_BARCODE,FINISHED_PRODUCT_ID.toString());
        singleReadyList.add(readyToDispatchContainerItem);
        readyToDispatchContainersRequest.setReadyToDispatchContainerItemList(singleReadyList);
        when(containerService.findById(OPEN_CONTAINER_ID)).thenReturn(openContainer);
        when(containerService.isContainerWithBarcode(CONTAINER_BARCODE)).thenReturn(false);
        when(productService.getProductById(FINISHED_PRODUCT_ID)).thenReturn(createFinishedProduct());
        when(containerService.save(openContainer)).thenReturn(readyContainer);
        ContainerActionResponse containerActionResponse = containerController.readyToDispatchContainers(readyToDispatchContainersRequest);
        assertEquals(MessageType.SUCCESS,containerActionResponse.getMessageType());
    }


    @Test
    void checkReadyContainerRequestFailure() {
        ReadyToDispatchContainersRequest readyToDispatchContainersRequest = new ReadyToDispatchContainersRequest();
        readyToDispatchContainersRequest.setReadyToDispatchBy(TEST_USER);
        List<ReadyToDispatchContainerItem> singleReadyList = new ArrayList<>();
        ReadyToDispatchContainerItem readyToDispatchContainerItem = new ReadyToDispatchContainerItem(openContainer.getId().toString(),CONTAINER_BARCODE,FINISHED_PRODUCT_ID.toString());
        singleReadyList.add(readyToDispatchContainerItem);
        readyToDispatchContainersRequest.setReadyToDispatchContainerItemList(singleReadyList);
        when(containerService.findById(OPEN_CONTAINER_ID)).thenReturn(openContainer);
        when(containerService.isContainerWithBarcode(CONTAINER_BARCODE)).thenReturn(true);
        ContainerActionResponse containerActionResponse = containerController.readyToDispatchContainers(readyToDispatchContainersRequest);
        assertEquals(MessageType.FAILURE,containerActionResponse.getMessageType());
    }


    @Test
    void checkDispatchContainerRequestSuccess() {
        DispatchContainersRequest dispatchContainersRequest = new DispatchContainersRequest();
        dispatchContainersRequest.setDispatchedBy(TEST_USER);
        List<String> barcodes = new ArrayList<>();
        barcodes.add(CONTAINER_BARCODE);
        dispatchContainersRequest.setBarcodeList(barcodes);
        when(containerService.findByBarcode(CONTAINER_BARCODE)).thenReturn(readyContainer);
        when(containerService.save(readyContainer)).thenReturn(dispatchedContainer);
        ContainerActionResponse containerActionResponse = containerController.dispatchContainers(dispatchContainersRequest);
        assertEquals(MessageType.SUCCESS,containerActionResponse.getMessageType());
    }



    @Test
    void checkDispatchContainerRequestFailure1() {
        DispatchContainersRequest dispatchContainersRequest = new DispatchContainersRequest();
        dispatchContainersRequest.setDispatchedBy(TEST_USER);
        List<String> barcodes = new ArrayList<>();
        barcodes.add(FAKE_CONTAINER_BARCODE);
        dispatchContainersRequest.setBarcodeList(barcodes);
        when(containerService.findByBarcode(FAKE_CONTAINER_BARCODE)).thenReturn(null);
        ContainerActionResponse containerActionResponse = containerController.dispatchContainers(dispatchContainersRequest);
        assertEquals(MessageType.FAILURE,containerActionResponse.getMessageType());
    }

    @Test
    void checkDispatchContainerRequestFailure2() {
        DispatchContainersRequest dispatchContainersRequest = new DispatchContainersRequest();
        dispatchContainersRequest.setDispatchedBy(TEST_USER);
        List<String> barcodes = new ArrayList<>();
        barcodes.add(CONTAINER_BARCODE);
        dispatchContainersRequest.setBarcodeList(barcodes);
        when(containerService.findByBarcode(CONTAINER_BARCODE)).thenReturn(openContainer);
        ContainerActionResponse containerActionResponse = containerController.dispatchContainers(dispatchContainersRequest);
        assertEquals(MessageType.FAILURE,containerActionResponse.getMessageType());
    }



    @Test
    void checkDeleteContainer() {
        doNothing().when(containerService).deleteContainer(DELETE_CONTAINER_ID);
        containerController.deleteContainer(DELETE_CONTAINER_ID.toString());

    }






}
