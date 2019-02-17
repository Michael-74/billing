package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.provider.SmotreshkaProvider;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.List;

@Service
public class SmotreshkaService {

    @Autowired
    SmotreshkaRepository smotreshkaRepository;

    private CompositSmotreshka cs = new CompositSmotreshka();

    public void load() {

        List<Smotreshka> smRepo = smotreshkaRepository.findAll();

        for(Smotreshka smotreshka: smRepo) {
            SmotreshkaProvider smotreshkaProvider = new SmotreshkaProvider();
            smotreshkaProvider.instance(smotreshka.getUrl(), smotreshka.getLogin(), smotreshka.getPassword());

            this.cs.addItems(smotreshkaProvider);
        }
    }

    public void sys() {
        System.out.println("sys");
        this.cs.getItems();
    }
}
