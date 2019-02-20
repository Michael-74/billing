package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.stereotype.Service;

@Service
@Getter @Setter
public class MikrotikProvider {

    public MikrotikProvider() {

    }

    public void getSys() throws MikrotikApiException {
        ApiConnection con = ApiConnection.connect("62.192.60.157"); // connect to router
        con.login("admin","njgjh2"); // log in to router

        System.out.println("Test");
    }
}
