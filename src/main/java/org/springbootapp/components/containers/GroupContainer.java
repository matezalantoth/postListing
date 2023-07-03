package org.springbootapp.components.containers;

import org.json.JSONObject;
import org.springbootapp.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class GroupContainer {
    private List<User> groupData;

    public GroupContainer() {
        this.groupData = new ArrayList<>();
    }

    public void addPusherData(JSONObject data) {
        User user = new User();
        Integer userId = data.getJSONObject("user").getInt("userId");
        user.setId(userId);
        groupData.add(user);

    }
    public List<User> getQueue() {

        return groupData;
    }

}

