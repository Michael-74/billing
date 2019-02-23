package ru.soyuz_kom.provider;

import ru.soyuz_kom.dto.smotreshka.*;

import java.util.List;

public interface ProviderSmotreshka {

    public Object addAccount(String username, String email, String password, List purchases);

    public AccountListDTO getAccounts();

    public AccountDTO getAccountById(String id);

    public AccountDTO setInfoOfAccount(String id, String date, String address, String fio, String period);

    public AccountPasswordStatusDTO setResetPasswordOfAccount(String id, String password);

    public AccountDeleteDTO deleteAccountById(String id);

    public List<AccountSubscriptionsDTO> getSubscriptionsOfAccount(String id);

    public SubscriptionDTO setSubscriptionOfAccount(String id, String subscriptionId, boolean isValid);

    public AccountDeleteDTO deleteAllSubscriptionsOfAccount(String id);
}
