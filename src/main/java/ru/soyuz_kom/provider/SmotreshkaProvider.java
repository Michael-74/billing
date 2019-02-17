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

    /**
     * Получаем всех абонентов со всеми их данными
     */
    public AccountListDTO getAccounts() {
        String str = "/v2/accounts";
        /*
        AccountListDTO response = restTemplate.getForObject(this.url + str, AccountListDTO.class);
        List<AccountDTO> accounts = response.getAccounts();
        */

        return (AccountListDTO) restTemplateHelper.exchange(this.url + str,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<AccountListDTO>() {}
        );
    }

    /**
     * Получаем одного абонента со всеми его данными
     * @param id
     * @return
     */

    public AccountDTO getAccountById(String id) {
        String str = "/v2/accounts/" + id;

        return (AccountDTO) restTemplateHelper.exchange(this.url + str,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<AccountDTO>() {}
        );

    }


    public AccountDTO setAccountInfo(String id, String date, String address, String fio, String period) {
        String str = "/v2/accounts/" + id + "/update";

        Map info = new HashMap<String, String>();
        AccountInfoDTO infoData = new AccountInfoDTO();
        infoData.setActivation_date(date);
        infoData.setAddress(address);
        infoData.setFio(fio);
        infoData.setPeriod(period);
        info.put("info", infoData);

        HttpEntity<?> httpEntity = new HttpEntity<Object>(info);

        return (AccountDTO) restTemplateHelper.exchange(this.url + str,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<AccountDTO>() {}
        );

    }

    /**
     * Сбрасываем пароль и ставим свой
     * @param id
     * @param password
     */
    public AccountPasswordStatusDTO setAccountPassword(String id, String password) {
        String str = "/v2/accounts/" + id + "/reset-password";
        Map info = new HashMap<String, String>();
        info.put("password", password);

        HttpEntity<?> httpEntity = new HttpEntity<Object>(info);

        return (AccountPasswordStatusDTO) restTemplateHelper.exchange(this.url + str,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<AccountPasswordStatusDTO>() {}
        );
    }

    /**
     * Удаление абонента по id
     * Если абонент был удален выкинет ошибку 403
     * @param id
     */
    public AccountDeleteDTO deleteAccountById(String id) {
        String str = "/v2/accounts/" + id;

        return (AccountDeleteDTO) restTemplateHelper
                .exchange(this.url + str,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<AccountDeleteDTO>() {}
                );
    }

    /**
     * Получаем все подписки абонента
     * @param id
     */
    public List<AccountSubscriptionsDTO> getSubscriptionsOfAccount(String id) {
        String str = "/v2/accounts/" + id + "/subscriptions";

        return (List<AccountSubscriptionsDTO>) restTemplateHelper
                .exchange(this.url + str,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<AccountSubscriptionsDTO>>() {
                });
    }

    /**
     * Изменяем тип подписки в зависимости от булева значения
     * @param id - номер акаунта
     * @param subscriptionId - номер подписки
     * @param isValid - добавить/удалить подписку
     */
    public SubscriptionDTO setSubscriptionOfAccount(String id, String subscriptionId, boolean isValid) {
        String str = "/v2/accounts/" + id + "/subscriptions";

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(subscriptionId);
        subscriptionDTO.setValid(isValid);
        HttpEntity<?> httpEntity = new HttpEntity<SubscriptionDTO>(subscriptionDTO);

       return (SubscriptionDTO) restTemplateHelper
                .exchange(
                        this.url + str,
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<SubscriptionDTO>() {}
                    );
    }

    /**
     * Удаление всех подписок
     * Ststus 200 - означает что запрос прошел
     * Метод возвращает delete: null, что значит не отследить верно ли прошел запрос, только по статусу определять
     * @param id
     */
    /*
    public void deleteAccountSubscriptions(String id) {
        String str = "/v2/accounts/" + id + "/subscriptions";

        ResponseEntity<AccountDeleteDTO> responseEntity =
                restTemplate.exchange(this.url + str,
                        HttpMethod.DELETE, null, new ParameterizedTypeReference<AccountDeleteDTO>() {
                        });
        AccountDeleteDTO accountDeleteDTO = responseEntity.getBody();
    }
    */
}
