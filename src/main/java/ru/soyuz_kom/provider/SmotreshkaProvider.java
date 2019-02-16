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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Получаем всех абонентов со всеми их данными
     */
    public List getAccounts() {
        String str = "/v2/accounts";

        AccountListDTO response = restTemplate.getForObject(this.url + str, AccountListDTO.class);
        List<AccountDTO> accounts = response.getAccounts();

        return accounts;
    }

    /**
     * Получаем одного абонента со всеми его данными
     * @param id
     * @return
     */
    public AccountDTO getAccount(String id) {
        String str = "/v2/accounts/{id}";

        return restTemplate.getForObject(this.url + str, AccountDTO.class, id);
    }

    public void setAccountInfo() {
        setAccountInfo(null, null, null, null, null);
    }

    public void setAccountInfo(String id, String date, String address, String fio, String period) {
        String str = "/v2/accounts/" + id + "/update";
        Map info = new HashMap<String, String>();
        AccountInfoDTO infoData = new AccountInfoDTO();
        infoData.setActivation_date(date);
        infoData.setAddress(address);
        infoData.setFio(fio);
        infoData.setPeriod(period);


        info.put("info", infoData);

        Object obj = restTemplate.postForObject(this.url + str, info, Object.class);
        System.out.println(obj);
    }

    /**
     * Сбрасываем пароль и ставим свой
     * @param id
     * @param password
     */
    public boolean setAccountPassword(String id, String password) {
        String str = "/v2/accounts/" + id + "/reset-password";
        Map info = new HashMap<String, String>();
        info.put("password", password);

        AccountPasswordStatusDTO status = restTemplate.postForObject(this.url + str, info, AccountPasswordStatusDTO.class);
        return status.getStatus().equals("ok");
    }

    /**
     * Удаление абонента по id
     * Если абонент был удален выкинет ошибку 403
     * @param id
     */
    public Boolean deleteAccountById(String id) {
        String str = "/v2/accounts/" + id;

        ResponseEntity<AccountDeleteDTO> response = restTemplate
                .exchange(this.url + str, HttpMethod.DELETE, null, AccountDeleteDTO.class);
        AccountDeleteDTO delete = response.getBody();
        return delete.getDelete() == true;
    }


    /**
     * Получаем все подписки абонента
     * @param id
     */
    public void getAccountSubscriptions(String id) {
        String str = "/v2/accounts/" + id + "/subscriptions";

        ResponseEntity<List<AccountSubscriptionsDTO>> responseEntity =
                restTemplate.exchange(this.url + str,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountSubscriptionsDTO>>() {
                        });
        List<AccountSubscriptionsDTO> listOfString = responseEntity.getBody();
    }

    /**
     * Изменяем тип подписки в зависимости от булева значения
     * @param id - номер акаунта
     * @param subscriptionId - номер подписки
     * @param isValid - добавить/удалить подписку
     */
    public void setAccountSubscriptions(String id, String subscriptionId, boolean isValid) {
        String str = "/v2/accounts/" + id + "/subscriptions";

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(subscriptionId);
        subscriptionDTO.setValid(isValid);
        HttpEntity<?> httpEntity = new HttpEntity<SubscriptionDTO>(subscriptionDTO);

        ResponseEntity<SubscriptionDTO> responseEntity =
                restTemplate.exchange(this.url + str,
                        HttpMethod.POST, httpEntity, new ParameterizedTypeReference<SubscriptionDTO>() {
                        });
        SubscriptionDTO subscriptionDTOResponse = responseEntity.getBody();
    }
}
