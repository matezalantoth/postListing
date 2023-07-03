package org.springbootapp.models;

import org.json.JSONObject;
import org.springbootapp.enums.GroupStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Group {
    private String link;
    private String name;
    private BigInteger id;
    private String image;
    private GroupStatus status;
    private LocalDateTime statusChangedAt;

    public Group(){
        this.status = GroupStatus.INIT;
    }

    public JSONObject getJSONGroupInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("link", link);
        jsonObject.put("name", name);
        jsonObject.put("image", image);
        return jsonObject;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GroupStatus getStatus() {
        return status;
    }

    public void setStatus(GroupStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }
}
