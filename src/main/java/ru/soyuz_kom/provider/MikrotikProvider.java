package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import me.legrange.mikrotik.ResultListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
public class MikrotikProvider {

    private ApiConnection con;
    public MikrotikProvider() {

    }

    /**
     * Подключение
     * @throws MikrotikApiException
     */
    public void connect(String ip, String login, String password) throws MikrotikApiException {
        this.con = ApiConnection.connect(ip); // connect to router
        this.con.login(login,password); // log in to router
    }

    /**
     * Вывести все записи
     * @return List
     * @throws MikrotikApiException
     */
    public List<Map<String, String>> getAll() throws MikrotikApiException {
        return this.con.execute("/ip/firewall/address-list/print");
    }

    /**
     * Команда на выполнение
     * @return
     * @throws MikrotikApiException
     */
    public List<Map<String, String>> exec() throws MikrotikApiException {
        /**
         * При удалении ничего не отдает, точнее List size = 0
         * При повторном удалении объекта которого уже нет выкидывает MikrotikApiException
         */
        //return this.con.execute("/ip/firewall/address-list/remove numbers=*77ED9");

        /**
         * При добавление отдает LinkedList с Хеш мапой, параметр ret = *74УВ2 (уникально присвоенный номер)
         */
        //return this.con.execute("/ip/firewall/address-list/add address=127.1.1.1 list=test comment=id_test");

        /**
         * При редактировании ничего не отдает, точнее List size = 0
         */
        return this.con.execute("/ip/firewall/address-list/set address=127.2.2.2 list=test2 comment=id_test2 numbers=*77F8B");
    }

    public boolean isConnect() throws MikrotikApiException {
        System.out.println("isConnect: " + this.con.isConnected());
        return this.con.isConnected();
    }
}
