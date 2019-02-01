package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Email;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

    List<Email> findAll();

    Optional<Email> findById(Integer id);
}
