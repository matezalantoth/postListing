package org.springbootapp.components.clients;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springbootapp.models.Group;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
@Component
public class GroupLinksClient {

    public void sendLinksToServer(List<Group> groups) {
        JSONArray linksJSON = new JSONArray();
        groups.forEach(group -> {
            JSONObject groupData = group.getJSONGroupInfo();
            linksJSON.put(groupData);
        });
        WebClient.create("groups.thesoftwareadvisor.co.uk/links")
                .post()
                .body(Mono.just(linksJSON), JSONArray.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }
}
