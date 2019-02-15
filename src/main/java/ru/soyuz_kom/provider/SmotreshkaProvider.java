package ru.soyuz_kom.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.Smotreshka;
import ru.soyuz_kom.repository.SmotreshkaRepository;

import java.util.List;

public class SmotreshkaProvider implements IProvider {

    @Autowired
    SmotreshkaRepository smotreshkaRepository;

    private List<Smotreshka> items;

    public SmotreshkaProvider() {
        //this.items = smotreshkaRepository.findAll();
    }

    public void makeInstance() {
        this.items = smotreshkaRepository.findAll();
    }

    public void getSys() {
        System.out.println("Smotreshka count: " + this.items.size());
    }
}
