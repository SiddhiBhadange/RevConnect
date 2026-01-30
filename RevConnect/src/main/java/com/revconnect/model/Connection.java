package com.revconnect.model;

public class Connection {
    public Connection(int id, int requesterId, int receiverId, String status) {
        this.id = id;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
    }

    private int id;

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", requesterId=" + requesterId +
                ", receiverId=" + receiverId +
                ", status='" + status + '\'' +
                '}';
    }

    private int requesterId;

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private int receiverId;
    private String status;
}
