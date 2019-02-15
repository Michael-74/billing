package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.dto.smotreshka.AccountDTO;
import ru.soyuz_kom.dto.smotreshka.AccountListDTO;

import java.util.List;

@Service
@Getter @Setter
public class SmotreshkaProvider {

    @Autowired
    private RestTemplate restTemplate;

    private String url;
    private String login;
    private String password;

    public SmotreshkaProvider instance(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;

        return this;
    }

    public void getAllItems() {
        String str = "/v2/accounts";
        System.out.println("getAllItems");


        AccountListDTO response = restTemplate.getForObject(this.url + str, AccountListDTO.class);
        List<AccountDTO> accounts = response.getAccounts();
        System.out.println(accounts.get(0).getInfo().getFio());

        /* Альтернатива
        ResponseEntity<AccountListDTO> rateResponse =
                restTemplate.exchange(this.url + str,
                            HttpMethod.GET, null, new ParameterizedTypeReference<AccountListDTO>() {}
                        );
        AccountListDTO accounts = rateResponse.getBody();
        System.out.println(accounts.getAccounts().get(0).getId());
        */

    }
}
