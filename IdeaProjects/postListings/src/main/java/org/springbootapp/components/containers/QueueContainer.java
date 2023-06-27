package org.springbootapp.components.containers;

import org.json.JSONObject;
import org.springbootapp.enums.DataStatus;
import org.springbootapp.models.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class QueueContainer {

    private List<Data> queueData;

    public QueueContainer(){

        this.queueData = new ArrayList<>();
    }


    public void addPusherData(JSONObject data) {
        Data newData = new Data();
        JSONObject userData = data.getJSONObject("user");
        JSONObject postData = data.getJSONObject("post");
        newData.setUser(userData);
        newData.setPost(postData);
        queueData.add(newData);
        queueData.forEach(queueDataEntry->{
            DataStatus currentStatus = queueDataEntry.getStatus();
            if (currentStatus == DataStatus.PROCESSED){
                queueData.remove(queueDataEntry);
            }
        });

    }

    public List<Data> getQueue() {
        return queueData;
    }

}
