package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Mikrotik;
import ru.soyuz_kom.entity.Sms;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmsRepository extends CrudRepository<Sms, Integer> {

    List<Sms> findAll();

    Optional<Sms> findById(Integer id);
}
