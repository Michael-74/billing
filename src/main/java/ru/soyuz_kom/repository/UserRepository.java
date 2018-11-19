package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    Iterable<User> findAll();

    Optional<User> findById(Integer id);

    User findByUsername(String username);
}
