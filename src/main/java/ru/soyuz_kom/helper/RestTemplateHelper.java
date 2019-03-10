package ru.soyuz_kom.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.service.Impl.LogSmotreshkaServiceImpl;

@Component
public class RestTemplateHelper {

    @Autowired
    RestTemplate restTemplate;

    private LogSmotreshkaServiceImpl logActionService;

    public RestTemplateHelper(LogSmotreshkaServiceImpl logActionService) {
        this.logActionService = logActionService;
    }

    public Object exchange(Client client, String methodName, String url, HttpMethod method, HttpEntity httpEntity, ParameterizedTypeReference object) {

        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    method,
                    httpEntity,
                    object
            );

            if(response.getStatusCode().value() == 200) {
                System.out.println("exchange pre-add");
                this.logActionService.push(url, String.valueOf(method), methodName, client,true, httpEntity, response.getBody());
                System.out.println("exchange add");
                return response.getBody();
            }
            System.out.println("exchange pre-add null");
            this.logActionService.push(url, String.valueOf(method), methodName, client,false, httpEntity, null);
            System.out.println("exchange add null");
            return null;
        } catch(Exception ex) {
            System.out.println("exchange pre-exception null");
            this.logActionService.push(url, String.valueOf(method), methodName, client,false, httpEntity, ex);
            System.out.println("exchange exception null");
            return null;
        }
    }
}
