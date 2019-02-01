package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Sms;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

    List<Sms> findAll();

    Optional<Sms> findById(Integer id);
}
