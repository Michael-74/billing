package ru.soyuz_kom.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.service.Impl.LogSmotreshkaServiceImpl;

@Component
public class RestTemplateHelper {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LogSmotreshkaServiceImpl logActionService;

    public Object exchange(String url, HttpMethod method, HttpEntity httpEntity, ParameterizedTypeReference object) {

        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    method,
                    httpEntity,
                    object
            );

            if(response.getStatusCode().value() == 200) {
                logActionService.push(url, String.valueOf(method), true, httpEntity, response.getBody());
                return response.getBody();
            }
            logActionService.push(url, String.valueOf(method), false, httpEntity, null);
            return null;
        } catch(Exception ex) {
            logActionService.push(url, String.valueOf(method), false, httpEntity, ex);
            return null;
        }
    }
}
