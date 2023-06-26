package org.boot.springbootapp.components.updaters;

import org.boot.springbootapp.components.enums.UserStatus;
import org.boot.springbootapp.components.models.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class UserUpdater {

    public void updateStatus(User user, UserStatus status) {

        LocalDateTime time = LocalDateTime.now();

        user.setStatus(status);
        user.setStatusChangedAt(time);
    }
}
