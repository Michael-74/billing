package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import me.legrange.mikrotik.ResultListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
public class MikrotikProvider {

    private ApiConnection con;
    private String pathAdressList = "/ip/firewall/address-list/";

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
     * @param str
     * @return
     */
    public List<Map<String, String>> exec(String str) {
        try {
            return this.con.execute(str);
        } catch(MikrotikApiException ex) {
            System.out.println("Error exec: " + str);
            return new ArrayList<>();
        }
    }

    /**
     * Поиск
     * @param type
     * @param value
     * @return
     */
    public List<Map<String, String>> search(String type, String value) {
        return this.exec(this.pathAdressList + "print" + " where " + type + "=" + value);
    }

    /**
     * Вывести все записи
     * @return List
     */
    public List<Map<String, String>> getAll() {
        return this.exec(this.pathAdressList + "print");
    }

    /**
     * При добавление отдает LinkedList с Хеш мапой, параметр ret = *74УВ2 (уникально присвоенный номер)
     */
    public List<Map<String, String>> create(String ip, String list, String comment) {
        return this.exec(this.pathAdressList + "add" + " address=" + ip + " list=" + list + " comment=" + comment);
    }

    /**
     * При редактировании ничего не отдает, точнее List size = 0
     */
    public List<Map<String, String>> update(String number, String ip, String list, String comment) {
        return this.exec(this.pathAdressList + "set" + "numbers=" + number + " address=" + ip + " list=" + list + " comment=" + comment);
    }

    /**
     * При удалении ничего не отдает, точнее List size = 0
     * При повторном удалении объекта которого уже нет выкидывает MikrotikApiException
     */
    public List<Map<String, String>> delete(String number) {
        return this.exec(this.pathAdressList + "remove" + "numbers=" + number);
    }
}
