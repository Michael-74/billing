package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.dto.smotreshka.*;
import ru.soyuz_kom.helper.RestTemplateHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
public class SmotreshkaProvider {

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    private String url;
    private String login;
    private String password;

    public SmotreshkaProvider instance(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;

        return this;
    }


    public Object addAccount(String username, String email, String password, List purchases) {
        String str = "/v2/accounts";

        Map<String, Object> addAccount = new HashMap<>();
        addAccount.put("username", username);
        addAccount.put("email", email);
        if(password != null){
            addAccount.put("password", password);
        }
        if(purchases != null){
            addAccount.put("purchases", purchases);
        }

        HttpEntity<?> httpEntity = new HttpEntity<Object>(addAccount);

        return restTemplateHelper.exchange(this.url + str,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<AccountNewResponseDTO>() {}
            );
    }


}
