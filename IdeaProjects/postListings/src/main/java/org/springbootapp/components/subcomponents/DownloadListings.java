package org.springbootapp.components.subcomponents;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springbootapp.components.clients.PostClient;
import org.springbootapp.components.containers.PostContainer;
import org.springbootapp.enums.PostStatus;
import org.springbootapp.enums.PusherStatus;
import org.springbootapp.models.Image;
import org.springbootapp.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DownloadListings {

    @Autowired
    private PostContainer postContainer;
    @Autowired
    private PostClient postClient;

    public void downloadPost(Post poster) throws Exception {

        JSONObject postData = postClient.getPost(poster.getPostId());

        addPostData(poster, postData);

        int linkaddress = (postData.getJSONObject("lead").getInt("leadId"));
        Integer.toString(linkaddress);
        poster.setLink(String.valueOf(linkaddress));

        List<Image> imageList = new ArrayList<>();
        JSONArray images = postData.getJSONObject("lead").getJSONArray("images");

        images.forEach(image -> {
            JSONObject imaginging = (JSONObject) image;

            Image nextImage = new Image();
            nextImage.setUrl(imaginging.getString("url"));
            nextImage.setPostId(poster.getPostId());

            try {
                nextImage.download();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            imageList.add(nextImage);
        });

        poster.setImages(imageList);

        poster.setStatus(PostStatus.DOWNLOADED);
        postContainer.addPost(poster);

    }

    private void addPostData(Post poster, JSONObject postData){

        poster.setPusherStatus(PusherStatus.IN_PROGRESS);
        poster.setScheduledTo(LocalDateTime.parse(postData.getString("scheduledTo"), DateTimeFormatter.ISO_DATE_TIME));
        poster.addGroup(postData.getJSONArray("facebook_groups"));
        poster.setUserId((postData.getInt("userId")));
        poster.setTitle(postData.getJSONObject("lead").getString("title"));
        poster.setDescription(postData.getJSONObject("lead").getString("description"));
        poster.setPrice(postData.getJSONObject("lead").getInt("price"));
        poster.setBeds(postData.getJSONObject("lead").getString("bed"));
        poster.setBath(postData.getJSONObject("lead").getString("bath"));
        poster.setPropSize(postData.getJSONObject("lead").getString("propSize"));
        poster.setAvailAt(postData.getJSONObject("lead").getString("availAt"));
        poster.setRentOrSale(postData.getJSONObject("lead").getInt("ros"));
        poster.setTypeOfHouse(postData.getJSONObject("lead").getInt("toh"));
        poster.setWashingMachineOrDryer(postData.getJSONObject("lead").getInt("wmod"));
        poster.setAirConditioningType(postData.getJSONObject("lead").getInt("act"));
        poster.setHeatingType(postData.getJSONObject("lead").getInt("ht"));
        poster.setPetFriendly(postData.getJSONObject("lead").getInt("pf"));
        poster.setParkingType(postData.getJSONObject("lead").getInt("pt"));
        poster.setPostOrList(postData.getJSONObject("lead").getInt("postOrList"));
    }
}
