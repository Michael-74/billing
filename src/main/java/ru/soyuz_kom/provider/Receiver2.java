package ru.soyuz_kom.provider;

public class Receiver2 {

    public void receiveMessage(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("LOG <" + message + ">");
    }
}
