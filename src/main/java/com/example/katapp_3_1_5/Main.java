package com.example.katapp_3_1_5;

import com.example.katapp_3_1_5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class Main {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String URL = "http://94.198.50.185:7081/api/users";


    @Autowired
    public Main(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
        this.headers.set("Cookie",
                String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    public String get() {
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(URL, HttpMethod.GET, entity, String.class).getBody();
    }

    public String create() {
        User user = new User(3L, "James", "Brown", (byte) 20);
        HttpEntity<User> entity = new HttpEntity<User>(user,headers);

        return restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();
    }

    public String update() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 5);
        HttpEntity<User> entity = new HttpEntity<User>(user,headers);

        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class, 3).getBody();
    }

    public String delete() {

        HttpEntity<User> entity = new HttpEntity<User>(headers);

        return restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class).getBody();
    }

    public String getAnswer() {
        return create() + update() + delete();
    }
}
