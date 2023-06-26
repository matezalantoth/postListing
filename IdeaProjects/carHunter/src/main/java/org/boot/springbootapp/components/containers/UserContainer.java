package org.boot.springbootapp.components.containers;

import org.boot.springbootapp.components.models.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@Component
public class UserContainer {

    private LinkedHashMap<Integer, User> fbUsers;
    private List<User> users;

    public UserContainer() {

        this.users = new ArrayList<>();
        fbUsers = new LinkedHashMap<>();

        User user = new User();
        user.setId(1);
        user.setEmail("fel.ujitas00@gmail.com");
        user.setPassword("felujitas$$");
        fbUsers.put(user.getId(), user);

    }

    public List<User> getQueue() {

        return users;
    }

    public void addUser(JSONObject data){
        JSONObject userData = data.getJSONObject("user");
        Integer userId = userData.getInt("userId");
        User user = fbUsers.get(userId);
        if (user != null){
            users.add(user);
            return;
        }
        User newUser = new User();
        newUser.setEmail(userData.getString("email"));
        newUser.setPassword(userData.getString("password"));
        newUser.setId(userId);
        fbUsers.put(newUser.getId(), newUser);
        users.add(user);
    }

    public User getFbUserByUserId(Integer userId){
        return fbUsers.get(userId);
    }

    public List<User> getUnderReviewUsers() {
        List<User> UnderReviewUsers = new ArrayList<>();
        fbUsers.forEach((userId, user) -> {
            if (user.isUnderReview()) {

                UnderReviewUsers.add(user);
            }
        });

        return UnderReviewUsers;
    }

}
