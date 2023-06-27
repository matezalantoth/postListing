package org.springbootapp.components.containers;

import org.json.JSONObject;
import org.springbootapp.enums.UserStatus;
import org.springbootapp.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@Component
public class UserContainer {


    private LinkedHashMap<Integer, User> fbUsers;


    public UserContainer() {
        fbUsers = new LinkedHashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("mztbusinessenquiries@gmail.com");
        user.setPassword("Mate'sPC190");
        fbUsers.put(user.getId(), user);
    }

    public void addUser(JSONObject userData){
        Integer userId = userData.getInt("userId");
        User user = fbUsers.get(userId);
        if (user != null){
            return;
        }
        User newUser = new User();
        newUser.setEmail(userData.getString("email"));
        newUser.setPassword(userData.getString("password"));
        newUser.setId(userId);
        fbUsers.put(newUser.getId(), newUser);
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

    public boolean canPost(User user){

        UserStatus currentStatus = user.getStatus();
        if (currentStatus == UserStatus.INVALID) {
            return false;
        }
        if (currentStatus == UserStatus.IN_USE) {
            return false;
        }

        return true;
    }

}
