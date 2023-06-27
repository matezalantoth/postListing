package org.springbootapp.components;

import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springbootapp.components.containers.GroupContainer;
import org.springbootapp.components.containers.QueueContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Pusher {

    @Autowired
    QueueContainer queueContainer;
    @Autowired
    GroupContainer groupContainer;


    @Value("${pusher.api.key}")
    String apiKey;
    @Value("${computer.id}")
    Integer computerId;

    @PostConstruct
    public void init() {
        PusherOptions pusherOptions = new PusherOptions().setCluster("eu");
        com.pusher.client.Pusher pusher = new com.pusher.client.Pusher(apiKey, pusherOptions);

        pusher.connect(new ConnectionEventListener() {

            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);


        Channel channel = pusher.subscribe("main");

        channel.bind("get-posts", new SubscriptionEventListener() {


            @Override
            public void onEvent(PusherEvent event) {

                System.out.println("Received spring custom event - " + event.getData());
                JSONObject data = new JSONObject(event.getData());
                Integer pusherComputerId = data.getJSONObject("computer").getInt("computerId");
                if (computerId != pusherComputerId) {
                    return;
                }
                queueContainer.addPusherData(data);

            }
        });

        channel.bind("get-links", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {

                System.out.println("Received spring custom event - " + event.getData());
                JSONObject data = new JSONObject(event.getData());
                Integer pusherComputerId = data.getJSONObject("computer").getInt("computerId");
                if (computerId != pusherComputerId) {
                    return;
                }
                groupContainer.addPusherData(data);
            }
        });
    }
}
