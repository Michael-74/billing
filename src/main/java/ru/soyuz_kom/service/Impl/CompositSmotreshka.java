package ru.soyuz_kom.service.Impl;

import ru.soyuz_kom.service.ISmotreshka;

import java.util.ArrayList;
import java.util.List;

public class CompositSmotreshka implements ISmotreshka {

    private List items = new ArrayList();

    public void addItems(Object item) {
        System.out.println("addItems");
        this.items.add(item);
    }

    public void getItems() {
        System.out.println("getItems");
        System.out.println(this.items);
    }
}
