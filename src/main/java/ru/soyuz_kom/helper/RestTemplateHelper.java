package ru.soyuz_kom.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateHelper {

    @Autowired
    RestTemplate restTemplate;

    public Object exchange2(String url, HttpMethod method, HttpEntity httpEntity, ParameterizedTypeReference object) {
        System.out.println("exchange: " + url);
        try {
            ResponseEntity response = restTemplate.exchange(url,
                    method,
                    httpEntity,
                    object
            );

            if(response.getStatusCode().value() == 200) {
                System.out.println("exchange body: " + response.getBody());
                return response.getBody();
            }
            System.out.println("exchange: " + url);
            return null;
        } catch(Exception ex) {
            System.out.println("exchange-error: " + ex);
            return null;
        }
    }
}
