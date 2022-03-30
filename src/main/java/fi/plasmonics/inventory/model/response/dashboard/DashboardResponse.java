package fi.plasmonics.inventory.model.response.dashboard;


public class DashboardResponse {

    private Long newContainerCount;
    private Long readyToDispatchContainerCount;
    private Long dispatchedContainerCount;
    private Long openContainerCount;


    public DashboardResponse(Long newContainerCount, Long readyToDispatchContainerCount, Long dispatchedContainerCount, Long openContainerCount) {
        this.newContainerCount = newContainerCount;
        this.readyToDispatchContainerCount = readyToDispatchContainerCount;
        this.dispatchedContainerCount = dispatchedContainerCount;
        this.openContainerCount = openContainerCount;
    }

    public Long getNewContainerCount() {
        return newContainerCount;
    }

    public void setNewContainerCount(Long newContainerCount) {
        this.newContainerCount = newContainerCount;
    }

    public Long getReadyToDispatchContainerCount() {
        return readyToDispatchContainerCount;
    }

    public void setReadyToDispatchContainerCount(Long readyToDispatchContainerCount) {
        this.readyToDispatchContainerCount = readyToDispatchContainerCount;
    }

    public Long getDispatchedContainerCount() {
        return dispatchedContainerCount;
    }

    public void setDispatchedContainerCount(Long dispatchedContainerCount) {
        this.dispatchedContainerCount = dispatchedContainerCount;
    }

    public Long getOpenContainerCount() {
        return openContainerCount;
    }

    public void setOpenContainerCount(Long openContainerCount) {
        this.openContainerCount = openContainerCount;
    }
}
