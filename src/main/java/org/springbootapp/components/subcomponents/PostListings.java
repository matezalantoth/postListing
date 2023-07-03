package org.springbootapp.components.subcomponents;

import org.openqa.selenium.WebDriver;
import org.springbootapp.components.browsers.FacebookBrowser;
import org.springbootapp.components.containers.PostContainer;
import org.springbootapp.components.containers.UserContainer;
import org.springbootapp.pages.GroupPage;
import org.springbootapp.components.updaters.PostUpdater;
import org.springbootapp.components.updaters.UserUpdater;
import org.springbootapp.enums.PostStatus;
import org.springbootapp.enums.UserStatus;
import org.springbootapp.models.Post;
import org.springbootapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PostListings {


    @Autowired
    private PostContainer postContainer;
    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private UserUpdater userUpdater;
    @Autowired
    private PostUpdater postUpdater;

    public PostListings() {
    }

    public void sendListings(Integer userId) {
        List<Post> posts = postContainer.getPosts();
        System.out.println(posts.size());
        for (Post post : posts) {

            if (!post.isDownloaded()) {
                continue;
            }
            try {
                postUpdater.updatePost(post, PostStatus.POSTING);
                User user = userContainer.getFbUserByUserId(userId);

                if (!userContainer.canPost(user)) {
                    continue;
                }

                userUpdater.updateStatus(user, UserStatus.IN_USE);
                WebDriver driver = facebookBrowser.getBrowser(user);

                try {

                    GroupPage groupPage = new GroupPage();

                    if (post.getPostOrList() == 0) {
                        groupPage.sendPost(post, driver);
                    }
                    if (post.getPostOrList() == 1) {
                        groupPage.postListing(post, driver);
                    }

                } catch (Exception e) {
                    String message = e.getMessage();
                    System.out.println(message);
                    userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
                    throw e;
                }
                userUpdater.updateStatus(user, UserStatus.NOT_IN_USE);
                postUpdater.updatePost(post, PostStatus.POSTED);


            } catch (Exception e) {
                System.out.println(e.getMessage());
                postUpdater.updatePost(post, PostStatus.FAILED);
            }
        }
        if (postContainer.getPosts().size() > 0) {
            sendListings(userId);
        }

    }
}
