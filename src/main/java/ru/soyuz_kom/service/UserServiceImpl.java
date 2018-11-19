package ru.soyuz_kom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.User;
import ru.soyuz_kom.repository.UserRepository;


import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> findAll () {
        return userRepository.findAll();
    }

    @Override
    public User getUsername(String name) {
        System.out.println("getUser: " + name);
        User user = userRepository.findByUsername(name);
        System.out.println("User: " + user);
        //System.out.println("ManyToMany: " + user.getRoles());

        return user;
    }
}
