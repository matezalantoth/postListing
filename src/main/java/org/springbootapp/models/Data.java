package org.springbootapp.models;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springbootapp.enums.DataStatus;

public class Data {

    private JSONObject user;
    private JSONObject post;
    private JSONArray group;
    private Integer userId;
    private Integer postId;
    private String groupName;
    private DataStatus status;

    public Data(){
        status = DataStatus.PROCESSING;
        group = new JSONArray();

    }

    public JSONObject getUser() {
        return user;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }

    public JSONObject getPost() {
        return post;
    }

    public void setPost(JSONObject post) {
        this.post = post;
    }

    public JSONArray getGroup() {
        return group;
    }

    public void setGroup(JSONArray group) {
        this.group = group;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
