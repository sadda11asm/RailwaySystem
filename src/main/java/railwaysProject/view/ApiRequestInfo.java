package railwaysProject.view;

import java.time.LocalDateTime;

public class ApiRequestInfo {
    String requestType;
    LocalDateTime dateTime;
    String addInfo;
    String whoIs;
    String action;

    public ApiRequestInfo(String requestType, LocalDateTime dateTime, String addInfo, String whoIs, String action) {
        this.requestType = requestType;
        this.dateTime = dateTime;
        this.addInfo = addInfo;
        this.whoIs = whoIs;
        this.action = action;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getWhoIs() {
        return whoIs;
    }

    public void setWhoIs(String whoIs) {
        this.whoIs = whoIs;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ApiRequestInfo{" +
                "requestType='" + requestType + '\'' +
                ", dateTime=" + dateTime +
                ", addInfo='" + addInfo + '\'' +
                ", whoIs='" + whoIs + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
