package org.springbootapp.components.updaters;

import org.json.JSONObject;
import org.springbootapp.enums.UserStatus;
import org.springbootapp.models.User;
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