package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import me.legrange.mikrotik.ResultListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.service.Impl.LogActionUserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
public class MikrotikProvider {

    LogActionUserServiceImpl logActionService;

    private ApiConnection con;
    private String pathAdressList = "/ip/firewall/address-list/";
    private final String ServiceName = "Mikrotik";

    public MikrotikProvider(LogActionUserServiceImpl logActionService) {
        this.logActionService = logActionService;
    }

    /**
     * Подключение
     */
    public void connect(String ip, String login, String password) {
        try {
            this.con = ApiConnection.connect(ip); // connect to router
            this.con.login(login,password); // log in to router
            System.out.println("Mikrotik connect: " + this.isConnect());
        } catch(MikrotikApiException ex) {
            System.out.println("Error connect");
        }
    }

    public void disconect() {
        try {
            this.con.close();
            System.out.println("Mikrotik close");
        } catch(MikrotikApiException ex) {
            System.out.println("Error close");
        }
    }


    /**
     * Команда на выполнение
     * @return
     * @throws MikrotikApiException
     *
    public List<Map<String, String>> exec() throws MikrotikApiException {
        //return this.con.execute("/ip/firewall/address-list/remove numbers=*77ED9");

        //return this.con.execute("/ip/firewall/address-list/add address=127.1.1.1 list=test comment=id_test");

        //return this.con.execute("/ip/firewall/address-list/set address=127.3.3.3 list=test2 comment=id_test3 numbers=*77F8B");

        //return this.con.execute("/ip/firewall/address-list/print where address=127.3.3.3");
    }
    */

    /**
     * Проверяет соединение
     * @return
     */
    public boolean isConnect() {
        return this.con.isConnected();
    }

    /**
     * Выполнение запросов
     * @param url
     * @return
     */
    public List<Map<String, String>> exec(Client client, String url, String serviceName, String methodClass, Map<String, String> request) {
        try {
            StringBuilder urlConcat = new StringBuilder(this.pathAdressList + url);

            for(Map.Entry<String, String> item: request.entrySet()) {
                urlConcat.append(" ").append(item.getKey()).append("=").append(item.getValue());
            }

            //this.pathAdressList
            //return this.con.execute(str);
            System.out.println("------------ MIKROTIK: " + urlConcat.toString());
            List<Map<String, String>> data = this.con.execute(urlConcat.toString());
            this.logActionService.push(urlConcat.toString(), methodClass, serviceName, methodClass, client,true, request, data);
            return data;
        } catch(Exception ex) {
            System.out.println("Error exec str: " + url);
            System.out.println("Error exec: " + ex);
            return new ArrayList<>();
        }
    }

    /**
     * Поиск
     * @param type
     * @param value
     * @return
     */
    public List<Map<String, String>> search(Client client, String type, String value) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("value", value);

        //return this.exec(this.pathAdressList + "print" + " where " + type + "=" + value);
        return this.exec(client, "print where", this.ServiceName, "search", map);
    }

    /**
     * Вывести все записи
     * @return List
     */
    public List<Map<String, String>> getAll(Client client) {
        Map map = new HashMap();
        //return this.exec(this.pathAdressList + "print");
        return this.exec(client, "print", this.ServiceName, "getAll", map);
    }

    /**
     * При добавление отдает LinkedList с Хеш мапой, параметр ret = *74УВ2 (уникально присвоенный номер)
     */
    public String create(Client client, String ip, String list, String comment) {
        Map map = new HashMap();
        map.put("address", ip);
        map.put("list", list);
        map.put("comment", comment);

        String mikrotikId = null;
        //List<Map<String, String>> data = this.exec(this.pathAdressList + "add " + " address=" + ip + " list=" + list + " comment=" + comment);
        List<Map<String, String>> data = this.exec(client, "add", this.ServiceName, "create", map);
        if(data.size() != 0) {
            mikrotikId = data.get(0).get("ret");
        }
        return mikrotikId;
    }

    /**
     * При редактировании ничего не отдает, точнее List size = 0
     */
    public List<Map<String, String>> update(Client client, String number, String ip, String list, String comment) {
        Map map = new HashMap();
        map.put("numbers", number);
        map.put("address", ip);
        map.put("list", list);
        map.put("comment", comment);

        //return this.exec(this.pathAdressList + "set " + "numbers=" + number + " address=" + ip + " list=" + list + " comment=" + comment);
        return this.exec(client, "set", this.ServiceName, "update", map);
    }

    /**
     * При удалении ничего не отдает, точнее List size = 0
     * При повторном удалении объекта которого уже нет выкидывает MikrotikApiException
     */
    public void delete(Client client, String number) {
        Map map = new HashMap();
        map.put("numbers", number);

        //this.exec(this.pathAdressList + "remove " + "numbers=" + number);
        //String url, String serviceName, String methodClass, Map request)
        this.exec(client, "remove", this.ServiceName, "delete", map);
    }
}
