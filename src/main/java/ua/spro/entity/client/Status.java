package ua.spro.entity.client;

public class Status {

    private Integer statusId;
    private String clientStatus;

    public Status(Integer statusId, String clientStatus) {
        this.statusId = statusId;
        this.clientStatus = clientStatus;
    }

    public Status(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    @Override
    public String toString() {
        return  clientStatus;
    }

    @Override
    public int hashCode() {
        return clientStatus.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Status status = (Status)obj;
                if (status != null)
        return this.getClientStatus().equalsIgnoreCase(status.getClientStatus());

        return super.equals(obj);
    }
}
