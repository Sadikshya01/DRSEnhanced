package model;

import java.time.LocalDateTime;

public class ResponseUpdate {

    private int updateId;
    private String status;
    private String description;
    private LocalDateTime updateTime;

    public ResponseUpdate() {
    }

    public ResponseUpdate(int updateId,
                          String status,
                          String description) {

        this.updateId = updateId;
        this.status = status;
        this.description = description;
        this.updateTime = LocalDateTime.now();
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void updateStatus(String status) {
        this.status = status;
        this.updateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return status + " - " + description;
    }
}