package org.springbootapp.components.schedulers;

import org.openqa.selenium.WebDriver;
import org.springbootapp.components.browsers.FacebookBrowser;
import org.springbootapp.components.clients.GroupLinksClient;
import org.springbootapp.components.containers.GroupContainer;
import org.springbootapp.components.containers.UserContainer;
import org.springbootapp.components.updaters.UserUpdater;
import org.springbootapp.enums.UserStatus;
import org.springbootapp.models.Group;
import org.springbootapp.models.User;
import org.springbootapp.pages.LinksPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GetGroups {

    @Autowired
    private FacebookBrowser facebookBrowser;
    @Autowired
    private UserContainer userContainer;
    @Autowired
    private GroupLinksClient groupLinksClient;
    @Autowired
    private UserUpdater userUpdater;
    @Autowired
    private GroupContainer groupContainer;

    @Scheduled(fixedRate = 60_000, initialDelay = 10_000)
    public void getLinks() throws Exception {
        List<User> users = groupContainer.getQueue();
        users.forEach(user -> {

            try {

                Integer userId = user.getId();

                User trueUser = userContainer.getFbUserByUserId(userId);

                WebDriver driver = facebookBrowser.getBrowser(trueUser);

                UserStatus currentStatus = user.getStatus();
                if (currentStatus == UserStatus.INVALID) {
                    return;
                }

                LinksPage groupLinksPage = new LinksPage(driver);
                List<Group> groups = groupLinksPage.getLinks();
                groupLinksClient.sendLinksToServer(groups);

            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
                userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
                facebookBrowser.closeBrowser(user);
            }
        });


    }
}
