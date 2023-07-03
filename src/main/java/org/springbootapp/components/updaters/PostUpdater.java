package org.springbootapp.components.updaters;

import org.json.JSONObject;
import org.springbootapp.enums.PostStatus;
import org.springbootapp.models.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class PostUpdater {

    public void updatePost(Post post, PostStatus postStatus){

        LocalDateTime time = LocalDateTime.now();

        post.setStatus(postStatus);
        post.setStatusChangedAt(time);

    }
}
