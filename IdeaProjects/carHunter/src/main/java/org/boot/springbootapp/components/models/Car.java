package org.boot.springbootapp.components.models;

import org.boot.springbootapp.components.enums.CarStatus;
import org.json.JSONObject;

import java.math.BigInteger;

public class Car {
    private Integer userId;
    private BigInteger marketplaceId;
    private String title;
    private String description;
    private String distance;
    private String price;
    private String link;
    private String image;
    private String imageContent;
    private String postedAt;
    private CarStatus status;

    public Car(){
        this.status = CarStatus.INIT;

    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public BigInteger getMarketplaceId() {
        return marketplaceId;
    }
    public void setMarketplaceId(BigInteger marketplaceId) {
        this.marketplaceId = marketplaceId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImageContent() {
        return imageContent;
    }
    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
    public String getPostedAt() {
        return postedAt;
    }
    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }
    public CarStatus getStatus() {
        return status;
    }
    public void setStatus(CarStatus status) {
        this.status = status;
    }
    public JSONObject getJSONInfo() {

        JSONObject jsonObject = new JSONObject();
        jsonData(jsonObject);
        return jsonObject;
    }
    public JSONObject jsonData(JSONObject jsonObject){

        jsonObject.put("title", title);
        jsonObject.put("description", description);
        jsonObject.put("image", image);
        jsonObject.put("distance", distance);
        jsonObject.put("price", price);
        jsonObject.put("link", link);
        jsonObject.put("marketplaceId", marketplaceId);
        jsonObject.put("imageContent", imageContent);
        jsonObject.put("postedAt", postedAt);
        return jsonObject;

    }
}
