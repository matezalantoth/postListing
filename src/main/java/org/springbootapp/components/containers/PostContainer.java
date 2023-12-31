package org.springbootapp.components.containers;

import org.springbootapp.models.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class PostContainer {

    private LinkedHashMap<Integer, Post> posts;

    public PostContainer() {
        posts = new LinkedHashMap<>();

    }
    public void addPost(Post post) {
        Integer postId = post.getPostId();
        if (posts.containsKey(postId)) {
            return;
        }
        posts.put(postId, post);
        System.out.println("added");


    }
    public List<Post> getPosts() {
        List<Post> selectedPosts = new ArrayList<>();
        posts.forEach((postId, post) -> {
            if (post.isDownloaded()) {

                selectedPosts.add(post);
            }
        });

        return selectedPosts;
    }


}