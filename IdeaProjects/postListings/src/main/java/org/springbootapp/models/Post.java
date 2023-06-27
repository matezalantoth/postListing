package org.springbootapp.models;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springbootapp.enums.PostStatus;
import org.springbootapp.enums.PusherStatus;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {

    private String title;
    private String description;
    private List<Image> images;
    private List<Group> groups;
    private Integer price;
    private String link;
    private Integer postId;
    private Integer userId;
    private PostStatus status;
    private PusherStatus pusherStatus;
    private LocalDateTime scheduledTo;
    private LocalDateTime statusChangedAt;
    private Integer postOrList;
    private Integer rentOrSale;
    private Integer typeOfHouse;
    private Integer washingMachineOrDryer;
    private Integer airConditioningType;
    private Integer heatingType;
    private Integer petFriendly;
    private Integer parkingType;
    private String beds;
    private String bath;
    private String propSize;
    private String availAt;
    private String address;

    public Post() {
        this.groups = new ArrayList<>();

        status = PostStatus.PENDING;
        pusherStatus = PusherStatus.INIT;
        rentOrSale = 0;
        typeOfHouse = 0;
        washingMachineOrDryer = 0;
        airConditioningType = 0;
        heatingType = 0;
        petFriendly = 0;
        parkingType = 0;
        postOrList = 1;
        beds = "3";
        bath = "1.5";
        propSize = "10,000";
        availAt = "6 9 2023";
        address = "Szeged 6722";
    }

    public void downloadImages() throws InterruptedException {

        images.forEach(image -> {
            try {
                image.download();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public String getImageLocation() {

        final StringBuilder imageLinkBuilder = new StringBuilder();

        images.forEach(image -> {
            if (!imageLinkBuilder.isEmpty()) {
                imageLinkBuilder.append("\n");
            }
            imageLinkBuilder.append(image.getLocalLink());
        });

        return (imageLinkBuilder.toString());

    }

    public void addGroup(JSONArray groupData){
        groupData.forEach(data ->{
            JSONObject trueData = (JSONObject) data;
            Group group = new Group();
            group.setName(trueData.getString("groupName"));
            group.setId(trueData.getBigInteger("groupId"));
            groups.add(group);
        });
    }

    public List<Group> getGroups(){
        return groups;
    }

    public boolean isDownloaded() {
        return status == PostStatus.DOWNLOADED;
    }

    public void setImages(List<Image> imagesList) {
        this.images = imagesList;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public BigInteger getFirstGroup() {
        return groups.get(0).getId();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public PusherStatus getPusherStatus() {
        return pusherStatus;
    }

    public void setPusherStatus(PusherStatus pusherStatus) {
        this.pusherStatus = pusherStatus;
    }

    public LocalDateTime getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(LocalDateTime scheduledTo) {
        this.scheduledTo = scheduledTo;
    }

    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    public Integer getPostOrList() {
        return postOrList;
    }

    public void setPostOrList(Integer postOrList) {
        this.postOrList = postOrList;
    }

    public Integer getRentOrSale() {
        return rentOrSale;
    }

    public void setRentOrSale(Integer rentOrSale) {
        this.rentOrSale = rentOrSale;
    }

    public Integer getTypeOfHouse() {
        return typeOfHouse;
    }

    public void setTypeOfHouse(Integer typeOfHouse) {
        this.typeOfHouse = typeOfHouse;
    }

    public Integer getWashingMachineOrDryer() {
        return washingMachineOrDryer;
    }

    public void setWashingMachineOrDryer(Integer washingMachineOrDryer) {
        this.washingMachineOrDryer = washingMachineOrDryer;
    }

    public Integer getAirConditioningType() {
        return airConditioningType;
    }

    public void setAirConditioningType(Integer airConditioningType) {
        this.airConditioningType = airConditioningType;
    }

    public Integer getHeatingType() {
        return heatingType;
    }

    public void setHeatingType(Integer heatingType) {
        this.heatingType = heatingType;
    }

    public Integer getPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(Integer petFriendly) {
        this.petFriendly = petFriendly;
    }

    public Integer getParkingType() {
        return parkingType;
    }

    public void setParkingType(Integer parkingType) {
        this.parkingType = parkingType;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getPropSize() {
        return propSize;
    }

    public void setPropSize(String propSize) {
        this.propSize = propSize;
    }

    public String getAvailAt() {
        return availAt;
    }

    public void setAvailAt(String availAt) {
        this.availAt = availAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
