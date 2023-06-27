package org.springbootapp.components.schedulers;

import org.json.JSONObject;
import org.springbootapp.components.containers.QueueContainer;
import org.springbootapp.components.containers.UserContainer;
import org.springbootapp.components.subcomponents.DownloadListings;
import org.springbootapp.components.subcomponents.PostListings;
import org.springbootapp.enums.DataStatus;
import org.springbootapp.enums.PostStatus;
import org.springbootapp.enums.PusherStatus;
import org.springbootapp.models.Data;
import org.springbootapp.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Posting {

    @Autowired
    DownloadListings downloadListings;
    @Autowired
    PostListings postListings;
    @Autowired
    UserContainer userContainer;
    @Autowired
    QueueContainer queueContainer;

    @Scheduled(fixedRate = 100_000, initialDelay = 10_000)
    public void downloadAndSendPosts() {
        List<Data> newDataList = queueContainer.getQueue();
        newDataList.forEach(data -> {
            DataStatus currentStatus = data.getStatus();

            if (currentStatus == DataStatus.PROCESSED){
                return;
            }
            JSONObject userData = data.getUser();
            JSONObject postData = data.getPost();
            Integer userId = userData.getInt("userId");
            Integer postId = postData.getInt("postId");
            Post post = new Post();
            post.setPostId(postId);
            try {

                userContainer.addUser(userData);
                downloadListings.downloadPost(post);
                postListings.sendListings(userId);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
                post.setPusherStatus(PusherStatus.FAILED);
                return;
            }
            post.setPusherStatus(PusherStatus.FINISHED);
            data.setStatus(DataStatus.PROCESSED);

        });
//        newDataList = queueContainer.getQueue();
//        if (newDataList.size() >0){
//            downloadAndSendPosts();
//        }

    }
}
