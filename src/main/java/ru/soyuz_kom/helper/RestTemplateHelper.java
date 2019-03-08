package ru.soyuz_kom.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.entity.LogAction;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.LogActionRepository;
import ru.soyuz_kom.repository.UserRepository;

@Component
public class RestTemplateHelper {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LogActionRepository logActionRepository;

    @Autowired
    UserRepository userRepository;

    public Object exchange(String url, HttpMethod method, HttpEntity httpEntity, ParameterizedTypeReference object) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        LogAction logAction = new LogAction();
        logAction.setUrl(url);
        logAction.setTypeAction(String.valueOf(method));
        logAction.setUserId(user.getId());
        logAction.setRequest(httpEntity);

        try {
            ResponseEntity response = restTemplate.exchange(
                    url,
                    method,
                    httpEntity,
                    object
            );

            if(response.getStatusCode().value() == 200) {
                System.out.println("exchange body: " + response.getBody());
                logAction.setIsSuccess(true);
                logAction.setResponse(response.getBody());
                logActionRepository.save(logAction);
                return response.getBody();
            }
            System.out.println("exchange: " + url);
            logAction.setIsSuccess(false);
            logAction.setResponse(null);
            logActionRepository.save(logAction);
            return null;
        } catch(Exception ex) {
            System.out.println("exchange-error: " + ex);
            logAction.setIsSuccess(false);
            logAction.setResponse(ex);
            logActionRepository.save(logAction);
            return null;
        }
    }
}
